package com.levirs.githubuser.data.github

import com.levirs.githubuser.core.model.SearchResult

class GithubRepository(private val service: GithubService) {
    suspend fun getUserList() = service.getUserList().map { it.toUser() }
    suspend fun searchUser(query: String) = service.searchUser(query).let {
        SearchResult(
            total = it.total,
            resultList = it.resultList.map { user ->
                user.toUser()
            }
        )
    }
    suspend fun getUserDetails(userName: String) = service.getUserDetails(userName).toUserDetails()
    suspend fun getUserFollowerList(userName: String)
            = service.getUserFollowerList(userName).map { it.toUser() }
    suspend fun getUserFollowingList(userName: String)
            = service.getUserFollowingList(userName).map { it.toUser() }
}