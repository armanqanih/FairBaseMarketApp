package org.lotka.xenon.domain.util

import org.lotka.xenon.domain.model.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)

