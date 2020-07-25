package com.levirs.githubuser.core.model

import com.google.gson.annotations.SerializedName

data class SearchResult<T>(
    @SerializedName("total_count")
    val total: Int,
    @SerializedName("items")
    val resultList: List<T>
)