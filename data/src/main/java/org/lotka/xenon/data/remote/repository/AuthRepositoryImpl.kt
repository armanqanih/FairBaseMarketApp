package org.lotka.xenon.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import ir.pinto.market.data.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.repository.AuthRepository
import org.lotka.xenon.domain.util.Resource
import java.security.MessageDigest
import java.util.UUID

import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val realtimeDatabase: FirebaseDatabase,
) : AuthRepository {
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading()) // Emit loading state
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    // Create User object
                    val newUser = User(
                        userId =  user.uid,
                        username = name,
                        email =  email,
                        profileImageUrl = user.photoUrl.toString()
                    )
                    // Save user data to Realtime Database
                    realtimeDatabase.reference.child("users").child(user.uid).setValue(newUser).await()
                    emit(Resource.Success(result))}
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    }

    override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading()) // Emit loading state
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    emit(Resource.Success(result)) // Emit success state
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error")) // Emit error state
            }
        }
    }

    override suspend fun googleSignIn(context: Context): Flow<Resource<AuthResult>> {
        return flow {
            try {
                val credentialManager: CredentialManager = CredentialManager.create(context)
                // Additional logging
                Log.d("AuthRepositoryImpl", "Credential Manager created.")

                val ranNonce: String = UUID.randomUUID().toString()
                val bytes: ByteArray = ranNonce.toByteArray()
                val md: MessageDigest = MessageDigest.getInstance("SHA-256")
                val digest: ByteArray = md.digest(bytes)
                val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }

                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true) // Try changing this to true
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setNonce(hashedNonce)
                    .setAutoSelectEnabled(true)
                    .build()

                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // Log before attempting to get the credential
                Log.d("AuthRepositoryImpl", "Attempting to get credential...")
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                // Check if the credential is valid
                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                    emit(Resource.Success(authResult))
                } else {
                    throw RuntimeException("Received an invalid credential type.")
                }

            } catch (e: GetCredentialCancellationException) {
                emit(Resource.Error("Sign-in was canceled."))
            } catch (e: NoCredentialException) {
                emit(Resource.Error("No credentials available. Please sign in manually."))
                Log.e("AuthRepositoryImpl", "No credentials available.", e)
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AuthRepositoryImpl", "Sign-in error: ${e.message}", e)
            }
        }
    }

    override suspend fun logOutUser(): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading()) // Emit loading state
                firebaseAuth.signOut() // Log out the user
                emit(Resource.Success(Unit)) // Emit success state with Unit
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error")) // Emit error state
            }
        }
    }

    }



