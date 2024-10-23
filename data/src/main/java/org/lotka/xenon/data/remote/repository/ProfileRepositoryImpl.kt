package org.lotka.xenon.data.remote.repository

import android.net.Uri
import android.util.Log
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
import org.lotka.xenon.data.remote.dao.local.database.ProfileDataBase


class ProfileRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth,
    private val realtimeDatabase: FirebaseDatabase,
    private val db: ProfileDataBase,
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading(true)) // Emit loading state

        try {

            val userSnapshot = realtimeDatabase.reference.child("users").child(userId).get().await()
            if (userSnapshot.exists()) {
                val user = userSnapshot.getValue(User::class.java)
                if (user != null) {
                    emit(Resource.Success(user))
                } else {
                    emit(Resource.Error("Failed to parse user data. Possible data schema mismatch."))
                }
            } else {
                emit(Resource.Error("User not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch user profile: ${e.localizedMessage}"))
            Log.e("ProfileRepository", "Error fetching user profile",e) // Log the error for debugging
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun upDateProfileData(
        updateProfileData: User,
        profileImageUri: Uri?,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading()) // Emit loading state

        try {
            // Step 1: Handle profile image upload (if provided)
            val profileImageUrl: String? = profileImageUri?.let { uri ->
                val storageReference = firebaseStorage.reference.child("profile_images/${updateProfileData.userId}.jpg")

                // Upload image to Firebase Storage and suspend until upload completes
                storageReference.putFile(uri).await()

                // Get the downloadable URL
                storageReference.downloadUrl.await().toString()
            }

            // Step 2: Update the user's profile data in Realtime Database
            val updatedUser = updateProfileData.copy(
                profileImageUrl = profileImageUrl ?: updateProfileData.profileImageUrl // Use new image URL if updated, or keep the old one
                , username = updateProfileData.username,
                  email = updateProfileData.email
            )

            // Assuming you have a reference to Firebase Realtime Database
            val databaseReference = realtimeDatabase.reference
                .child("users")
                .child(updatedUser.userId)
            databaseReference.setValue(updatedUser).await() // Suspend until Realtime Database operation completes

            emit(Resource.Success(updatedUser))

        } catch (e: Exception) {
            // Step 4: Emit an error if something went wrong
            emit(Resource.Error("Error updating profile: ${e.message}"))
        }
    }


}

