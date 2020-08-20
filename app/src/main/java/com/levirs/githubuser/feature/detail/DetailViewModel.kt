package com.levirs.githubuser.feature.detail

import android.app.Application
import android.database.ContentObserver
import android.os.Handler
import androidx.lifecycle.*
import com.levirs.githubuser.data.DataProvider
import com.levirs.githubuser.util.toLiveData
import com.levirs.githubuser.util.updateFromCoroutine
import com.levirs.githubuser.common.model.DataState
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.common.model.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }

    private val mGithubRepository = DataProvider.provideGithubRepository()
    private val mUserFavoriteRepository = DataProvider.provideFavoriteUserRepository(application)
    private val mIOScope = viewModelScope + Dispatchers.IO
    private lateinit var mCurrentUser: User
    private val mUserDetails = MutableLiveData(DataState<UserDetails>())
    private val mFollowingList = MutableLiveData(DataState<List<User>>())
    private val mFollowerList = MutableLiveData(DataState<List<User>>())
    private val mIsFavorite = MutableLiveData<Boolean>()
    val userDetails = mUserDetails.toLiveData()
    val followingList = mFollowingList.toLiveData()
    val followerList = mFollowerList.toLiveData()
    val isFavorite: LiveData<Boolean> = mIsFavorite
    var isInitialized = false

    fun init() {
        mUserFavoriteRepository.registerObserver(mContentObserver)
        isInitialized = true
    }

    override fun onCleared() {
        super.onCleared()
        mUserFavoriteRepository.unregisterObserver(mContentObserver)
    }

    private fun updateIsFavorite() = mIOScope.launch {
        mIsFavorite.postValue(mUserFavoriteRepository.isFavorite(mCurrentUser))
    }

    fun setUser(user: User) {
        mCurrentUser = user
        updateIsFavorite()
        load()
        loadFollowingList()
        loadFollowerList()
    }

    fun toggleFavorite() = mIOScope.launch {
        if (isFavorite.value!!)
            mUserFavoriteRepository.removeFromFavorite(mCurrentUser)
        else mUserFavoriteRepository.addToFavorite(mCurrentUser)
    }

    fun load() {
        mUserDetails.updateFromCoroutine(mIOScope, suspend {
            mGithubRepository.getUserDetails(mCurrentUser.userName)
        })
    }

    fun loadFollowingList() {
        mFollowingList.updateFromCoroutine(mIOScope,  suspend {
            mGithubRepository.getUserFollowingList(mCurrentUser.userName)
        })
    }

    fun loadFollowerList() {
        mFollowerList.updateFromCoroutine(mIOScope, suspend {
            mGithubRepository.getUserFollowerList(mCurrentUser.userName)
        })
    }

    private val mContentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            updateIsFavorite()
        }
    }
}