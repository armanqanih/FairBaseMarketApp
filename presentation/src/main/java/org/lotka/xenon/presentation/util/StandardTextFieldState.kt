package org.lotka.xenon.presentation.util

import org.lotka.xenon.domain.util.error.Error

data class StandardTextFieldState(
    val text : String = "",
    val error: Error? = null
)