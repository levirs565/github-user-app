package com.levirs.githubuser.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.model.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>
fun <T> MutableLiveData<T>.update(action: T.() -> Unit) {
    val value = this.value
    value?.action()
    postValue(value)
}

fun <T> MutableLiveData<DataState<T>>.updateFromCoroutine(
    scope: CoroutineScope,
    action: suspend () -> T
): Job {
    return scope.launch {
        update {
            data = null
            error = null
        }
        try {
            val newData = action()
            update {
                data = newData
            }
        } catch (e: IOException) {
            update {
                error = e.message
            }
        }
    }
}

fun <T> CoroutineScope.launchToUpdateLiveData(
    liveData: MutableLiveData<DataState<T>>,
    action: suspend () -> T
) {
}