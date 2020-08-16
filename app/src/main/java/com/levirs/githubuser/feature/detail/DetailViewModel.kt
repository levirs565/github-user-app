package com.levirs.githubuser.feature.detail

import android.app.Application
import androidx.lifecycle.*
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.extension.toLiveData
import com.levirs.githubuser.core.extension.updateFromCoroutine
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.model.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }

    private val mRepository = CoreProvider.provideRepository()
    private val mUserFavoriteRepository = CoreProvider.provideUserFavoriteRepository(application)
    private val mIOScope = viewModelScope + Dispatchers.IO
    private lateinit var mCurrentUser: User
    private val mUserDetails = MutableLiveData(DataState<UserDetails>())
    private val mFollowingList = MutableLiveData(DataState<List<User>>())
    private val mFollowerList = MutableLiveData(DataState<List<User>>())
    val userDetails = mUserDetails.toLiveData()
    val followingList = mFollowingList.toLiveData()
    val followerList = mFollowerList.toLiveData()
    lateinit var isFavorite: LiveData<Boolean>

    fun setUser(user: User) {
        mCurrentUser = user
        isFavorite = mUserFavoriteRepository.isFavorite(mCurrentUser)
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
            mRepository.getUserDetails(mCurrentUser.userName)
        })
    }

    fun loadFollowingList() {
        mFollowingList.updateFromCoroutine(mIOScope,  suspend {
            mRepository.getUserFollowingList(mCurrentUser.userName)
        })
    }

    fun loadFollowerList() {
        mFollowerList.updateFromCoroutine(mIOScope, suspend {
            mRepository.getUserFollowerList(mCurrentUser.userName)
        })
    }
}