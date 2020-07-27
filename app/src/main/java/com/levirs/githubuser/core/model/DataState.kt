package com.levirs.githubuser.core.model

data class DataState<T>(
    var data: T? = null,
    var error: String? = null
)