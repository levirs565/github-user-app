package com.levirs.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    val id: Int,
    @SerializedName("login")
    val userName: String,
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val company: String? = null,
    val location: String? = null,
    @SerializedName("public_repos")
    val repositoryCount: Int,
    @SerializedName("following")
    val followingCount: Int,
    @SerializedName("followers")
    val followersCount: Int
)