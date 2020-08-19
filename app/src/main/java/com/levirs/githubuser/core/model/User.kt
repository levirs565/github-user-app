package com.levirs.githubuser.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val userName: String,
    val avatar: String
) : Parcelable