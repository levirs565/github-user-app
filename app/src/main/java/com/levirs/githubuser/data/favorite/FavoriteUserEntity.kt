package com.levirs.githubuser.data.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.levirs.githubuser.core.model.User

@Entity(tableName = "user")
data class UserFavoriteEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_name")
    val userName: String,
    val avatar: String
)

fun User.toEntity(): UserFavoriteEntity
    = UserFavoriteEntity(
    id = this.id,
    userName = this.userName,
    avatar = this.avatar
)

fun UserFavoriteEntity.toUser(): User
    = User(
    id = this.id,
    userName = this.userName,
    avatar = this.avatar
)