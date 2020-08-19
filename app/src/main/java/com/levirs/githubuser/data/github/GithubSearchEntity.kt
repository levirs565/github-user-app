package com.levirs.githubuser.data.github

import com.google.gson.annotations.SerializedName

data class GithubSearchEntity<T>(
    @SerializedName("total_count")
    val total: Int,
    @SerializedName("items")
    val resultList: List<T>
)