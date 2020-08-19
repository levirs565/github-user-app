package com.levirs.githubuser.common.model

data class SearchResult<T>(
    val total: Int,
    val resultList: List<T>
)