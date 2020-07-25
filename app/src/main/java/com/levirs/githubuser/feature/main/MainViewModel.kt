package com.levirs.githubuser.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.model.User
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {
    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    private val mRepository = CoreProvider.provideRepository()
    private val mIOScope = viewModelScope + Dispatchers.IO
    private var mJob: Job? = null
    private val mUserList =  MutableLiveData<List<User>?>()
    val userList: LiveData<List<User>?> = mUserList

    private fun cancel() = mJob?.run {
        if (isActive)
            cancel()
    }

    fun fetchUserList() {
        Log.d(TAG, "fetchUserList: start..")
        cancel()
        mUserList.value = null
        mJob = mIOScope.launch {
            Log.d(TAG, "fetchUserList: launched")
            mUserList.postValue(mRepository.getUserList())
            Log.d(TAG, "fetchUserList: finish")
        }
    }

    fun searchUser(query: String) {
        Log.d(TAG, "searchUser: start..")
        cancel()
        mUserList.value = null
        mJob = mIOScope.launch {
            Log.d(TAG, "searchUser: launched")
            mUserList.postValue(mRepository.searchUser(query).resultList)
            Log.d(TAG, "searchUser: finish")
        }
    }
}