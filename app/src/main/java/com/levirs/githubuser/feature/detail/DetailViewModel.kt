package com.levirs.githubuser.feature.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.extension.launchToUpdateLiveData
import com.levirs.githubuser.core.extension.toLiveData
import com.levirs.githubuser.core.extension.update
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.model.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.io.IOException

class DetailViewModel: ViewModel() {
    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }

    private val mRepository = CoreProvider.provideRepository()
    private val mIOScpe = viewModelScope + Dispatchers.IO
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
        mIOScpe.launchToUpdateLiveData(mUserDetails, suspend {
            mRepository.getUserDetails(mCurrentUser.userName)
        })
    }

    fun loadFollowingList() {
        mIOScpe.launchToUpdateLiveData(mFollowingList, suspend {
            mRepository.getUserFollowingList(mCurrentUser.userName)
        })
    }

    fun loadFollowerList() {
        mIOScpe.launchToUpdateLiveData(mFollowerList, suspend {
            mRepository.getUserFollowerList(mCurrentUser.userName)
        })
    }
}