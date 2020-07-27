package com.levirs.githubuser.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>
fun <T> MutableLiveData<T>.update(action: T.() -> Unit) {
    val value = this.value
    value?.action()
    postValue(value)
}