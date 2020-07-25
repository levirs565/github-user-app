package com.levirs.githubuser.repository

import com.levirs.githubuser.model.User
import com.levirs.githubuser.service.GithubService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GithubRepositoryTest {
    companion object {
        const val MY_USERNAME = "levirs565"
        const val MY_NAME = "Levi Rizki Saputra"
        const val MY_LOCATION = "Indonesia"
    }
    private var mService = GithubService.newInstance()
    private var mRepository = GithubRepository(mService)

    fun checkUserList(list: List<User>) {
        for (user in list) {
            assertNotNull(user.id)
            assertNotNull(user.userName)
            assertNotNull(user.avatar)
        }
    }

    @Test
    fun getUserList() = runBlocking {
        val result = mRepository.getUserList()
        checkUserList(result)
    }

    @Test
    fun searchUser() = runBlocking {
        val result = mRepository.searchUser(MY_USERNAME)
        val mine = result.resultList[0]
        assertEquals(mine.userName, MY_USERNAME)
    }

    @Test
    fun getUserDetails() = runBlocking {
        val result = mRepository.getUserDetails(MY_USERNAME)
        assertEquals(result.userName, MY_USERNAME)
        assertEquals(result.name, MY_NAME)
        assertEquals(result.location, MY_LOCATION)
    }

    @Test
    fun getUserFollowerList() = runBlocking {
        val result = mRepository.getUserFollowerList(MY_USERNAME)
        checkUserList(result)
    }

    @Test
    fun getUserFollowingList() = runBlocking {
        val result = mRepository.getUserFollowingList(MY_USERNAME)
        checkUserList(result)
    }
}