package com.levirs.githubuser.consumer

import android.app.Application
import android.database.ContentObserver
import android.os.Handler
import androidx.lifecycle.*
import com.levirs.githubuser.common.data.favorite.FavoriteUserRepository
import com.levirs.githubuser.common.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mIOScope = viewModelScope + Dispatchers.IO
    private val mFavoriteUserRepository = FavoriteUserRepository(application)
    private val mFavoriteList = MutableLiveData<List<User>>()
    val favoriteList: LiveData<List<User>> = mFavoriteList
    var isInitialized = false

    fun init() {
        mFavoriteUserRepository.registerObserver(mContentObserver)
        update()
        isInitialized = true
    }

    override fun onCleared() {
        mFavoriteUserRepository.unregisterObserver(mContentObserver)
    }

    fun update() = mIOScope.launch {
        mFavoriteList.postValue(mFavoriteUserRepository.getAllFavorite())
    }

    private val mContentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            update()
        }
    }
}