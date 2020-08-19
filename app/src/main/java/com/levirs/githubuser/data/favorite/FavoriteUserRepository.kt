package com.levirs.githubuser.data.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.levirs.githubuser.core.model.User

class FavoriteUserRepository(private val dao: FavoriteUserDao) {

    fun getAllFavorite() = dao.getAll().map {
        it.map { entity -> entity.toUser() }
    }

    fun isFavorite(user: User): LiveData<Boolean> {
        return dao.getByUserName(user.userName).map {
            it != null
        }
    }

    suspend fun addToFavorite(user: User) = dao.insert(user.toEntity())
    suspend fun removeFromFavorite(user: User) = dao.delete(user.toEntity())

}