package com.levirs.githubuser.feature.favorite

import android.app.Application
import android.database.ContentObserver
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.data.DataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserFavoriteRepository = DataProvider.provideFavoriteUserRepository(application)
    private val mUserFavoriteList = MutableLiveData<List<User>>()
    private val mIOScope = viewModelScope + Dispatchers.IO
    val userFavoriteList: LiveData<List<User>> = mUserFavoriteList
    var isInitialized = false

    fun init() {
        updateFavoriteList()
        mUserFavoriteRepository.registerObserver(mContentObserver)
        isInitialized = true
    }

    override fun onCleared() {
        super.onCleared()
        mUserFavoriteRepository.unregisterObserver(mContentObserver)
    }

    private fun updateFavoriteList() = mIOScope.launch {
        mUserFavoriteList.postValue(mUserFavoriteRepository.getAllFavorite())
    }

    private val mContentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            updateFavoriteList()
        }
    }
}