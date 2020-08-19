package com.levirs.githubuser.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levirs.githubuser.data.favorite.FavoriteUserDao
import com.levirs.githubuser.data.favorite.UserFavoriteEntity

@Database(entities = [UserFavoriteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        fun newInstance(context: Context): AppDatabase
            = Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}