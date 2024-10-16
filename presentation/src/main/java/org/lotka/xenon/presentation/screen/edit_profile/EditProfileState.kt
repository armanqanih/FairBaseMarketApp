package org.lotka.xenon.presentation.screen.edit_profile


import android.net.Uri
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.presentation.util.StandardTextFieldState


data class EditProfileState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val user : User? = null,
    val profileImageUri: Uri? = null,

    val userNameState : StandardTextFieldState = StandardTextFieldState(),
    val familyNameState : StandardTextFieldState = StandardTextFieldState(),
    val emailState : StandardTextFieldState = StandardTextFieldState(),





    )
