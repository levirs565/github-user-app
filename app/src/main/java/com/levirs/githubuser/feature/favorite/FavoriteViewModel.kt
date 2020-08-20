package com.levirs.githubuser.feature.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.levirs.githubuser.data.DataProvider

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserFavoriteRepository = DataProvider.provideFavoriteUserDataSource(application)
    val userFavoriteList = mUserFavoriteRepository.getAllFavorite()
}