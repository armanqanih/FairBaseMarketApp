package org.lotka.xenon.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.util.Resource


interface ProfileRepository {

    suspend fun getProfile(userId: String): Flow<Resource<User>>
    suspend fun upDateProfileData(updateProfileData: User, profileImageUri: Uri?): Flow<Resource<User>>


}