package com.levirs.githubuser.feature.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.levirs.githubuser.core.CoreProvider

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserFavoriteRepository = CoreProvider.provideUserFavoriteRepository(application)
    val userFavoriteList = mUserFavoriteRepository.getAllFavorite()
}