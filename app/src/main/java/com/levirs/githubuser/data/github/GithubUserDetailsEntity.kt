package com.levirs.githubuser.data.github

import com.google.gson.annotations.SerializedName
import com.levirs.githubuser.common.model.UserDetails

data class GithubUserDetailsEntity(
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

fun GithubUserDetailsEntity.toUserDetails() =
    UserDetails(
        id = this.id,
        userName = this.userName,
        name = this.name,
        avatar = this.avatar,
        company = this.company,
        location = this.location,
        repositoryCount = this.repositoryCount,
        followingCount = this.followingCount,
        followersCount = this.followersCount
    )