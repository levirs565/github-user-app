package com.levirs.githubuser.common.data.favorite

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.toContentValues
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.toMultipleUser
import com.levirs.githubuser.common.model.User

class FavoriteUserRepository(
    context: Context
) {
    private val mResolver = context.contentResolver

    fun getAllFavorite(): List<User> {
        val cursor = mResolver.query(
            CONTENT_URI,
            null, null, null, null
        )
        val result = cursor!!.toMultipleUser()
        cursor.close()
        return result
    }

    fun isFavorite(user: User): Boolean {
        val path = "${CONTENT_URI}/${user.id}"
        val cursor = mResolver.query(
            Uri.parse(path),
            null, null, null, null
        )
        // moveToFirst akan return false jika cursor kosong
        val result = cursor!!.moveToFirst()
        cursor.close()
        return result
    }

    fun addToFavorite(user: User) {
        mResolver.insert(CONTENT_URI, user.toContentValues())
    }

    fun removeFromFavorite(user: User) {
        val path = "$CONTENT_URI/${user.id}"
        mResolver.delete(Uri.parse(path), null, null)
    }

    fun registerObserver(observer: ContentObserver) {
        mResolver.registerContentObserver(CONTENT_URI, true, observer)
    }

    fun unregisterObserver(observer: ContentObserver) {
        mResolver.unregisterContentObserver(observer)
    }
}