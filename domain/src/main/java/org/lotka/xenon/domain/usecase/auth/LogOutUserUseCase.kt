package org.lotka.xenon.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.repository.AuthRepository
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.domain.util.error.AuthError
import org.lotka.xenon.domain.util.result.LoginResult

import javax.inject.Inject

class LogOutUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<Unit>>{
        return repository.logOutUser()
    }


}