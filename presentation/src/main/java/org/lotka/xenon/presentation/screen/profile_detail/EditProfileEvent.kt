package org.lotka.xenon.presentation.screen.profile_detail

import android.net.Uri
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.StandardTextFieldState

sealed class EditProfileEvent {

    data class UserNameChange(val userNameState : StandardTextFieldState) : EditProfileEvent()
    data class FamilyNameChange(val familyName : StandardTextFieldState) : EditProfileEvent()
    data class EmailChange(val email : StandardTextFieldState) : EditProfileEvent()
    data class PasswordChange(val password : PasswordTextFieldState) : EditProfileEvent()


    data class CropProfilePicture(val uri : Uri) : EditProfileEvent()



    object UpdateProfile : EditProfileEvent()
    object IsPasswordVisibility : EditProfileEvent()

}