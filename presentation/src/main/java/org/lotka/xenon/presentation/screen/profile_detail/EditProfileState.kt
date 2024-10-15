package org.lotka.xenon.presentation.screen.profile_detail


import android.net.Uri
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.StandardTextFieldState


data class EditProfileState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val profile : User? = null,
    val profileImageUri: Uri? = null,

    val userNameState : StandardTextFieldState = StandardTextFieldState(),
    val familyNameState : StandardTextFieldState = StandardTextFieldState(),
    val emailState : StandardTextFieldState = StandardTextFieldState(),





    )
