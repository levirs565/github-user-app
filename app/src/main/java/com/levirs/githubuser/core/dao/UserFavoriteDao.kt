package com.levirs.githubuser.core.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.levirs.githubuser.core.model.User

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE user_name = :userName")
    fun getByUserName(userName: String): LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}