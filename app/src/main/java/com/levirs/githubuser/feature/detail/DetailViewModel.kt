package com.levirs.githubuser.feature.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levirs.githubuser.core.CoreProvider
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

    data class UserDetailsState(
        var data: UserDetails? = null,
        var error: String? = null
    )

    private val mRepository = CoreProvider.provideRepository()
    private val mIOScpe = viewModelScope + Dispatchers.IO
    private lateinit var mCurrentUser: User
    private val mUserDetails = MutableLiveData<UserDetailsState>(UserDetailsState())
    val userDetails: LiveData<UserDetailsState> = mUserDetails

    private fun updateUserDetails(action: UserDetailsState.() -> Unit) {
        val state = mUserDetails.value!!
        state.action()
        mUserDetails.postValue(state)
    }

    fun setUser(user: User) {
        mCurrentUser = user
        load()
    }

    fun load() {
        mIOScpe.launch {
            updateUserDetails {
                data = null
                error = null
            }
            try {
                val details = mRepository.getUserDetails(mCurrentUser.userName)
                updateUserDetails {
                    data = details
                }
            } catch (e: IOException) {
                updateUserDetails {
                    error = e.message
                }
            }
        }
    }
}