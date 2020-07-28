package com.levirs.githubuser.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.extension.toLiveData
import com.levirs.githubuser.core.extension.updateFromCoroutine
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.User
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception

class MainViewModel: ViewModel() {
    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    private val mRepository = CoreProvider.provideRepository()
    private val mIOScope = viewModelScope + Dispatchers.IO
    private var mJob: Job? = null
    private val mUserList = MutableLiveData(DataState<List<User>>())
    val userList = mUserList.toLiveData()

    private fun cancel() = mJob?.run {
        if (isActive)
            cancel()
    }

    fun fetchUserList() {
        Log.d(TAG, "fetchUserList: start..")
        cancel()
        mJob = mUserList.updateFromCoroutine(mIOScope, suspend {
            mRepository.getUserList()
        })
    }

    fun searchUser(query: String) {
        Log.d(TAG, "searchUser: start..")
        cancel()
        mJob = mUserList.updateFromCoroutine(mIOScope, suspend {
            mRepository.searchUser(query).resultList
        })
    }
}