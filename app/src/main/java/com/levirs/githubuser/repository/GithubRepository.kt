package com.levirs.githubuser.repository

import com.levirs.githubuser.service.GithubService

class GithubRepository(private val service: GithubService) {
    suspend fun getUserList() = service.getUserList()
    suspend fun searchUser(query: String) = service.searchUser(query)
    suspend fun getUserDetails(userName: String) = service.getUserDetails(userName)
    suspend fun getUserFollowerList(userName: String) = service.getUserFollowerList(userName)
    suspend fun getUserFollowingList(userName: String) = service.getUserFollowingList(userName)
}