package com.levirs.githubuser.data.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.levirs.githubuser.common.model.User

class FavoriteUserDataSource(private val dao: FavoriteUserDao) {

    fun getAllFavorite() = dao.getAll()
    fun getById(id: Int) = dao.getById(id)

    fun addToFavorite(user: User) = dao.insert(user.toEntity())
    fun removeFromFavorite(user: User) = dao.delete(user.toEntity())

}