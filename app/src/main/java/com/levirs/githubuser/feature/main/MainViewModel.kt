package com.levirs.githubuser.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
import com.levirs.githubuser.core.extension.toLiveData
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
    private val mUserList = MutableLiveData<List<User>?>()
    private val mError = MutableLiveData<String>()
    val userList = mUserList.toLiveData()
    val isError = mError.toLiveData()

    private fun cancel() = mJob?.run {
        if (isActive)
            cancel()
    }

    fun fetchUserList() {
        Log.d(TAG, "fetchUserList: start..")
        cancel()
        mUserList.value = null
        mJob = mIOScope.launch {
            try {
                Log.d(TAG, "fetchUserList: launched")
                mUserList.postValue(mRepository.getUserList())
                Log.d(TAG, "fetchUserList: finish")
            } catch (e: IOException) {
                Log.d(TAG, "fetchUserList: error ${e.message}")
                e.printStackTrace()
                mError.postValue(e.message)
            }
        }
    }

    fun searchUser(query: String) {
        Log.d(TAG, "searchUser: start..")
        cancel()
        mUserList.value = null
        mJob = mIOScope.launch {
            try {
                Log.d(TAG, "searchUser: launched")
                mUserList.postValue(mRepository.searchUser(query).resultList)
                Log.d(TAG, "searchUser: finish")
            } catch (e: IOException) {
                Log.d(TAG, "searchUser: error ${e.message}")
                e.printStackTrace()
                mError.postValue(e.message)
            }
        }
    }
}