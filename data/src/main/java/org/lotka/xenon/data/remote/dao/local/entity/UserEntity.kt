package org.lotka.xenon.data.remote.dao.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.data.util.Constants.ITEM_TABLE
import org.lotka.xenon.data.util.Constants.USER_TABLE
import org.lotka.xenon.domain.model.User


@Entity(tableName = USER_TABLE)
data class UserEntity (
    @PrimaryKey val userId:String,
    val username : String,
    val family:String,
    val profileImageUrl : String?=null,
    val email:String? = null
    
)

fun UserEntity.toUser(): User {
    return User(
        userId = this.userId,
        username = this.username,
        family = this.family,
        profileImageUrl = this.profileImageUrl,
        email = this.email
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        username = this.username,
        family = this.family,
        profileImageUrl = this.profileImageUrl,
        email = this.email
    )
}

