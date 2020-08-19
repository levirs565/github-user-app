package com.levirs.githubuser.core.model

data class SearchResult<T>(
    val total: Int,
    val resultList: List<T>
)