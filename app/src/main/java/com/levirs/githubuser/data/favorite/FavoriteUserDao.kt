package com.levirs.githubuser.data.favorite

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Cursor

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserFavoriteEntity): Long

    @Delete
    fun delete(user: UserFavoriteEntity): Int
}