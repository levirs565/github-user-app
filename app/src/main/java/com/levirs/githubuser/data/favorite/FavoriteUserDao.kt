package com.levirs.githubuser.data.favorite

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<UserFavoriteEntity>>

    @Query("SELECT * FROM user WHERE user_name = :userName")
    fun getByUserName(userName: String): LiveData<UserFavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserFavoriteEntity)

    @Delete
    suspend fun delete(user: UserFavoriteEntity)
}