package org.lotka.xenon.domain.usecase.profile

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.repository.ProfileRepository
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(updateProfileData: User, profileImageUri: Uri?): Flow<Resource<User>> {
        return repository.upDateProfileData(updateProfileData, profileImageUri)
    }
}
