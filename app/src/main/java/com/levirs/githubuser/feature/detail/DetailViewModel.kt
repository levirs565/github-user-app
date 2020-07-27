package com.levirs.githubuser.feature.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
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
    val userDetails = mUserDetails.toLiveData()

    fun setUser(user: User) {
        mCurrentUser = user
        load()
    }

    fun load() {
        mIOScpe.launch {
            mUserDetails.update {
                data = null
                error = null
            }
            try {
                val details = mRepository.getUserDetails(mCurrentUser.userName)
                mUserDetails.update {
                    data = details
                }
            } catch (e: IOException) {
                mUserDetails.update {
                    error = e.message
                }
            }
        }
    }
}