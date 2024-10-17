package org.lotka.xenon.domain.util.error

sealed class EditProfileError : Error() {
    object FieldEmpty :  EditProfileError()
}