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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lotka.xenon.data.remote.dao.local.database.ProfileDataBase
import org.lotka.xenon.data.remote.dao.local.entity.toItems
import org.lotka.xenon.data.remote.dao.local.entity.toItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.toUser
import org.lotka.xenon.data.remote.dao.local.entity.toUserEntity
import org.lotka.xenon.domain.model.Item
class ProfileRepositoryImpl @Inject constructor(
    private val realtimeDatabase: FirebaseDatabase,
    private val storage: FirebaseStorage,
    private val db: ProfileDataBase
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading(true))

        try {
            // Check if the item exists in Room first
            val cachedUser = db.profileDao().getUserById(userId)
            if (cachedUser != null) {
                emit(Resource.Success(cachedUser.toUser()))
            } else {
                // If item is not found in Room, fetch from Firebase
                val itemReference = realtimeDatabase.getReference("users/$userId")
                val snapshot = itemReference.get().await()

                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    db.profileDao().saveUserData(user.toUserEntity())
                    emit(Resource.Success(user))
                } else {
                    emit(Resource.Error("User not found in Firebase"))
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
        profileImageUri: Uri?  // Optional image URI
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        try {
            // 1. Check if the user is authenticated
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                emit(Resource.Error("User is not authenticated. Please sign in first."))
                return@flow
            }

            // 2. Upload profile image if present
            val profileImageUrl = profileImageUri?.let {
                val storageRef = storage.reference.child("profile_images/${updateProfileData.userId}")
                try {
                    storageRef.putFile(it).await()  // Upload image to Firebase Storage
                    storageRef.downloadUrl.await().toString()  // Get the download URL
                } catch (e: Exception) {
                    Log.e("ProfileRepository", "Failed to upload profile image", e)
                    emit(Resource.Error("Failed to upload profile image: ${e.message}"))
                    return@flow
                }
            }

            // 3. Update user data in Firebase Realtime Database
            val updatedUser = updateProfileData.copy(
                profileImageUrl = profileImageUrl ?: updateProfileData.profileImageUrl
            )

            val userReference = realtimeDatabase.getReference("users/${updatedUser.userId}")
            try {
                userReference.setValue(updatedUser).await()  // Update user in Realtime Database
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Failed to update user data in Firebase", e)
                emit(Resource.Error("Failed to update user data: ${e.message}"))
                return@flow
            }

            // 4. Update the user profile in Room (local database)
            db.profileDao().saveUserData(updatedUser.toUserEntity())

            emit(Resource.Success(updatedUser))

        } catch (e: Exception) {
            Log.e("ProfileRepository", "Failed to update profile", e)
            emit(Resource.Error("Error updating profile: ${e.message}"))
        }
    }
}
