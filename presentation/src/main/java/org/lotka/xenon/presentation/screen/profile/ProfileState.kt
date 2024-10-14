package org.lotka.xenon.presentation.screen.profile

import org.lotka.xenon.domain.model.User

data class ProfileState(
    val isLoading : Boolean = false,
    val profile : User? = null,

    )
