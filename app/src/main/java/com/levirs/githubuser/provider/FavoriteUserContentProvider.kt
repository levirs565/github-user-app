package com.levirs.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.AUTHORITY
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.FavoriteUserColumns.Companion.TABLE_NAME
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.toUser
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.data.DataProvider
import com.levirs.githubuser.data.favorite.FavoriteUserDataSource

class FavoriteUserContentProvider : ContentProvider() {
    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_USER_ID = 2

        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                TYPE_USER
            )
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                TYPE_USER_ID
            )
        }
    }

    private lateinit var mDataSource: FavoriteUserDataSource

    override fun onCreate(): Boolean {
        mDataSource = DataProvider.provideFavoriteUserDataSource(context!!)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = when (sUriMatcher.match(uri)) {
            TYPE_USER -> mDataSource.getAllFavorite()
            TYPE_USER_ID -> mDataSource.getById(uri.lastPathSegment!!.toInt())
            else -> throw  IllegalArgumentException("Unknown URI: $uri")
        }
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (sUriMatcher.match(uri) == TYPE_USER) {
            val user = values!!.toUser()
            val result = mDataSource.addToFavorite(user)
            if (result != 0L) {
                context?.contentResolver?.notifyChange(CONTENT_URI, null)
                return Uri.parse("$CONTENT_URI/$result")
            } else throw Exception("Cannot insert $user")
        }
        throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated = when (sUriMatcher.match(uri)) {
            TYPE_USER_ID -> {
                mDataSource.addToFavorite(values!!.toUser())
                1
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted = when (sUriMatcher.match(uri)) {
            TYPE_USER_ID -> mDataSource.removeFromFavorite(User(
                id = uri.lastPathSegment!!.toInt(),
                userName = "",
                avatar = ""
            ))
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}
