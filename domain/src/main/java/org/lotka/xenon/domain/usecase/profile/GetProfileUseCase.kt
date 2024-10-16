package org.lotka.xenon.domain.usecase.profile

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.repository.ProfileRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<User>> {
        return repository.getProfile(userId)
    }
}
