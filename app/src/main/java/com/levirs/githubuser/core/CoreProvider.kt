package com.levirs.githubuser.core

import android.content.Context
import com.levirs.githubuser.core.database.AppDatabase
import com.levirs.githubuser.core.repository.GithubRepository
import com.levirs.githubuser.core.repository.UserFavoriteRepository
import com.levirs.githubuser.core.service.GithubService

object CoreProvider {
    private val mGithubService by lazy {
        GithubService.newInstance()
    }
    private val mGithubRepository by lazy {
        GithubRepository(mGithubService)
    }
    @Volatile private var mAppDatabase: AppDatabase? = null
    @Volatile private var mUserFavoriteRepository: UserFavoriteRepository? = null

    private fun provideAppDatabase(context: Context): AppDatabase
        = mAppDatabase ?: synchronized(AppDatabase::class) {
        mAppDatabase ?: AppDatabase.newInstance(context).also { mAppDatabase = it }
    }

    fun provideRepository() = mGithubRepository
    fun provideUserFavoriteRepository(context: Context): UserFavoriteRepository
        = mUserFavoriteRepository ?: synchronized(UserFavoriteRepository::class) {
        mUserFavoriteRepository
            ?: UserFavoriteRepository(provideAppDatabase(context).userFavoriteDao()).also {
                mUserFavoriteRepository = it
            }
    }
}