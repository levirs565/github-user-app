package com.levirs.githubuser.model

data class UserDetails(
    val id: Int,
    val userName: String,
    val name: String,
    val avatar: Int,
    val company: String,
    val location: String,
    val repository: Int,
    val following: Int,
    val follower: Int
)