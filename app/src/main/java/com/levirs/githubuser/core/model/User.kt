package com.levirs.githubuser.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatar: String
): Parcelable