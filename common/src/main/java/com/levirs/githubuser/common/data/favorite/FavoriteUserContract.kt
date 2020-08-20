package com.levirs.githubuser.common.data.favorite

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import com.levirs.githubuser.common.model.User

object FavoriteUserContract {
    const val SCHEME = "content"
    const val AUTHORITY = "com.levirs.githubuser"

    class FavoriteUserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USER_NAME = "user_name"
            const val AVATAR = "avatar"

            val CONTENT_URI = Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    fun ContentValues.toUser(): User = User(
        id = getAsInteger(FavoriteUserColumns.ID),
        userName = getAsString(FavoriteUserColumns.USER_NAME),
        avatar = getAsString(FavoriteUserColumns.AVATAR)
    )

    fun User.toContentValues(): ContentValues = ContentValues().apply {
        put(FavoriteUserColumns.ID, id)
        put(FavoriteUserColumns.USER_NAME, userName)
        put(FavoriteUserColumns.AVATAR, avatar)
    }

    fun Cursor.toMultipleUser(): List<User> {
        val result = arrayListOf<User>()

        while (moveToNext()) {
            result.add(User(
                id = getInt(getColumnIndex(FavoriteUserColumns.ID)),
                userName = getString(getColumnIndex(FavoriteUserColumns.USER_NAME)),
                avatar = getString(getColumnIndex(FavoriteUserColumns.AVATAR))
            ))
        }

        return result
    }
}