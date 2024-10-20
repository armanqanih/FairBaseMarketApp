package org.lotka.xenon.domain.usecase.auth

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.repository.AuthRepository
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.domain.util.error.AuthError
import org.lotka.xenon.domain.util.result.LoginResult

import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke (context: Context): Flow<Resource<AuthResult>> {
        return repository.googleSignIn(context)

    }
}