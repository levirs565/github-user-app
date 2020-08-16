package com.levirs.githubuser.core.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_name")
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatar: String
): Parcelable