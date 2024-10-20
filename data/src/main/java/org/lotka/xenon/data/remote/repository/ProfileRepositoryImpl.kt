package org.lotka.xenon.data.remote.repository



import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.repository.ProfileRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject
import android.util.Log
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.lotka.xenon.data.remote.dao.local.database.ProfileDataBase
import org.lotka.xenon.data.remote.dao.local.entity.toItems
import org.lotka.xenon.data.remote.dao.local.entity.toItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.toUser
import org.lotka.xenon.data.remote.dao.local.entity.toUserEntity
import org.lotka.xenon.domain.model.Item
class ProfileRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore, // Inject Firestore
    private val db: ProfileDataBase
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading(true))

        try {
            val currentUser = firebaseAuth.currentUser ?: run {
                emit(Resource.Error("User is not authenticated. Please sign in first."))
                return@flow
            }

            val firebaseUserProfile = User(
                userId = currentUser.uid,
                email = currentUser.email ?: "",
                username = currentUser.displayName ?: "Unknown User",
                profileImageUrl = currentUser.photoUrl?.toString() ?: "",
            )

            // Check if the user exists in the local Room database
            val cachedUser = db.profileDao().getUserById(currentUser.uid)
            if (cachedUser != null) {
                emit(Resource.Success(cachedUser.toUser())) // Return cached user
            } else {
                // If no cache, use Firestore to fetch user data
                val firestoreUser = firestore.collection("users").document(currentUser.uid).get().await()
                if (firestoreUser.exists()) {
                    val userData = firestoreUser.toObject(User::class.java)
                    userData?.let {
                        db.profileDao().saveUserData(it.toUserEntity()) // Save to local Room database
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error("User data not found in Firestore."))
                } else {
                    // Save the FirebaseAuth user info if no Firestore data exists
                    db.profileDao().saveUserData(firebaseUserProfile.toUserEntity())
                    emit(Resource.Success(firebaseUserProfile))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch user profile: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun upDateProfileData(
        updateProfileData: User,
        profileImageUri: Uri?
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val currentUser = firebaseAuth.currentUser ?: run {
                emit(Resource.Error("User is not authenticated. Please sign in first."))
                return@flow
            }

            // Upload profile image and get URL if present
            val profileImageUrl = profileImageUri?.let {
                val storageRef = firebaseStorage.reference.child("profile_images/${currentUser.uid}")
                storageRef.putFile(it).await()
                storageRef.downloadUrl.await().toString()
            } ?: updateProfileData.profileImageUrl // Fallback to existing URL if no new image

            // Update FirebaseAuth user profile
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(updateProfileData.username)
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build()

            currentUser.updateProfile(profileUpdates).await()

            // Update user in Firestore
            val userDocument = firestore.collection("users").document(currentUser.uid)
            userDocument.set(updateProfileData.copy(profileImageUrl = profileImageUrl)).await()

            // Save the updated user data in the local database
            db.profileDao().saveUserData(updateProfileData.copy(profileImageUrl = profileImageUrl).toUserEntity())
            emit(Resource.Success(updateProfileData.copy(profileImageUrl = profileImageUrl)))

        } catch (e: Exception) {
            emit(Resource.Error("Error updating profile: ${e.message}"))
        }
    }
}


