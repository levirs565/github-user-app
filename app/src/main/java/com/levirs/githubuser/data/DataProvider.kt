package com.levirs.githubuser.data

import android.content.Context
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
    @Volatile private var mFavoriteUserRepository: FavoriteUserDataSource? = null

    private fun provideAppDatabase(context: Context): AppDatabase
        = mAppDatabase ?: synchronized(AppDatabase::class) {
        mAppDatabase ?: AppDatabase.newInstance(context).also { mAppDatabase = it }
    }

    fun provideGithubRepository() =
        mGithubRepository
    fun provideFavoriteUserDataSource(context: Context): FavoriteUserDataSource
        = mFavoriteUserRepository ?: synchronized(FavoriteUserDataSource::class) {
        mFavoriteUserRepository
            ?: FavoriteUserDataSource(
                provideAppDatabase(
                    context
                ).favoriteUserDao()
            ).also {
                mFavoriteUserRepository = it
            }
    }
}