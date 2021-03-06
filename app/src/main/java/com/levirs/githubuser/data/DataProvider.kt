package com.levirs.githubuser.data

import android.content.Context
import com.levirs.githubuser.common.data.favorite.FavoriteUserRepository
import com.levirs.githubuser.data.github.GithubRepository
import com.levirs.githubuser.data.favorite.FavoriteUserDataSource
import com.levirs.githubuser.data.github.GithubService

object DataProvider {
    private val mGithubService by lazy {
        GithubService.newInstance()
    }
    private val mGithubRepository by lazy {
        GithubRepository(mGithubService)
    }
    @Volatile private var mAppDatabase: AppDatabase? = null
    @Volatile private var mFavoriteUserDataSource: FavoriteUserDataSource? = null
    @Volatile private var mFavoriteUserRepository: FavoriteUserRepository? =null

    private fun provideAppDatabase(context: Context): AppDatabase
        = mAppDatabase ?: synchronized(AppDatabase::class) {
        mAppDatabase ?: AppDatabase.newInstance(context).also { mAppDatabase = it }
    }

    fun provideGithubRepository() =
        mGithubRepository
    fun provideFavoriteUserDataSource(context: Context): FavoriteUserDataSource
        = mFavoriteUserDataSource ?: synchronized(FavoriteUserDataSource::class) {
        mFavoriteUserDataSource
            ?: FavoriteUserDataSource(
                provideAppDatabase(
                    context
                ).favoriteUserDao()
            ).also {
                mFavoriteUserDataSource = it
            }
    }
    fun provideFavoriteUserRepository(context: Context): FavoriteUserRepository
        = mFavoriteUserRepository ?: synchronized(FavoriteUserRepository::class) {
        mFavoriteUserRepository ?: FavoriteUserRepository(context)
    }
}