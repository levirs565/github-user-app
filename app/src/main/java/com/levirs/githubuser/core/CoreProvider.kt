package com.levirs.githubuser.core

import com.levirs.githubuser.core.repository.GithubRepository
import com.levirs.githubuser.core.service.GithubService

object CoreProvider {
    private val mGithubService by lazy {
        GithubService.newInstance()
    }
    private val mGithubRepository by lazy {
        GithubRepository(mGithubService)
    }

    fun provideRepository() = mGithubRepository

}