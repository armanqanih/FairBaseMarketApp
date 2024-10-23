package org.lotka.xenon.domain.model

data class User (
    val userId:String="",
    val username : String="",
    val email:String? = null,
    val profileImageUrl : String?=null,
)