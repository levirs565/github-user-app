package com.levirs.githubuser.data.github

import com.google.gson.annotations.SerializedName
import com.levirs.githubuser.common.model.User

data class GithubUserEntity(
    val id: Int,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatar: String
)

fun GithubUserEntity.toUser() = User(
    id = this.id,
    userName = this.userName,
    avatar = this.avatar
)