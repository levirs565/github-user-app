package com.levirs.githubuser.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>
fun <T> MutableLiveData<T>.update(action: T.() -> Unit) {
    val value = this.value
    value?.action()
    postValue(value)
}

fun <T> CoroutineScope.launchToUpdateLiveData(
    liveData: MutableLiveData<DataState<T>>,
    action: suspend () -> T
) {
    launch {
        liveData.update {
            data = null
            error = null
        }
        try {
            val follower = action()
            liveData.update {
                data = follower
            }
        } catch (e: IOException) {
            liveData.update {
                error = e.message
            }
        }
    }
}