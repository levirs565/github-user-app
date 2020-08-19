package com.levirs.githubuser.common.model

data class UserDetails(
    val id: Int,
    val userName: String,
    val name: String,
    val avatar: String,
    val company: String? = null,
    val location: String? = null,
    val repositoryCount: Int,
    val followingCount: Int,
    val followersCount: Int
)