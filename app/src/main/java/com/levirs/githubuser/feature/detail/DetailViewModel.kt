package com.levirs.githubuser.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.extension.toLiveData
import com.levirs.githubuser.core.extension.updateFromCoroutine
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.model.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus

class DetailViewModel: ViewModel() {
    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }

    private val mRepository = CoreProvider.provideRepository()
    private val mIOScope = viewModelScope + Dispatchers.IO
    private lateinit var mCurrentUser: User
    private val mUserDetails = MutableLiveData(DataState<UserDetails>())
    private val mFollowingList = MutableLiveData(DataState<List<User>>())
    private val mFollowerList = MutableLiveData(DataState<List<User>>())
    val userDetails = mUserDetails.toLiveData()
    val followingList = mFollowingList.toLiveData()
    val followerList = mFollowerList.toLiveData()

    fun setUser(user: User) {
        mCurrentUser = user
        load()
        loadFollowingList()
        loadFollowerList()
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