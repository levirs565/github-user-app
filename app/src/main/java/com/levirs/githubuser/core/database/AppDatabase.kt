package com.levirs.githubuser.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levirs.githubuser.core.dao.UserFavoriteDao
import com.levirs.githubuser.core.model.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        fun newInstance(context: Context): AppDatabase
            = Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
    }

    abstract fun userFavoriteDao(): UserFavoriteDao
}