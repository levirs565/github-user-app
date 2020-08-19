package com.levirs.githubuser.common.model

data class DataState<T>(
    var data: T? = null,
    var error: String? = null
)