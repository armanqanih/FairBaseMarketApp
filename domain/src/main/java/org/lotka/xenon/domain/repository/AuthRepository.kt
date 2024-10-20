package org.lotka.xenon.domain.repository

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.util.Resource


interface AuthRepository{

    suspend fun registerUser(name:String,email: String, password: String):Flow<Resource<AuthResult>>

    suspend fun loginUser(email: String, password: String):Flow<Resource<AuthResult>>

    suspend fun googleSignIn(context: Context): Flow<Resource<AuthResult>>

    suspend fun logOutUser(): Flow<Resource<Unit>>

}

