package org.lotka.xenon.domain.model

data class User (
    val userId:String="",
    val username : String="",
    val family:String="",
    val profileImageUrl : String?=null,
    val email:String? = null,
    val haveAccount: Boolean = false
    
)