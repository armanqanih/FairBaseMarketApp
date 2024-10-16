package org.lotka.xenon.presentation.screen.profile

import android.net.Uri
import org.lotka.xenon.domain.model.User

data class ProfileState(
    val user : User? = null,
    val profileImageUri: Uri? = null,
    val isLoading : Boolean = false,
    val error : String? = null,

    )
