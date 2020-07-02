package com.levirs.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val userName: String,
    val name: String,
    val avatar: Int,
    val company: String
): Parcelable