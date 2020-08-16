package com.levirs.githubuser.core.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.levirs.githubuser.core.dao.UserFavoriteDao
import com.levirs.githubuser.core.model.User

class UserFavoriteRepository(private val dao: UserFavoriteDao) {

    fun getAllFavorite() = dao.getAll()

    fun isFavorite(user: User): LiveData<Boolean> {
        return dao.getByUserName(user.userName).map {
            it != null
        }
    }

    suspend fun addToFavorite(user: User) = dao.insert(user)
    suspend fun removeFromFavorite(user: User) = dao.delete(user)

}