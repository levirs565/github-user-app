package com.levirs.githubuser.data.favorite

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.levirs.githubuser.common.data.favorite.FavoriteUserContract.toMultipleUser
import com.levirs.githubuser.data.AppDatabase
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteUserDataSourceTest {
    private val mockUser = User(
        id = 1,
        userName = "tai",
        avatar = "blank"
    )
    private lateinit var favoriteUserRepository: FavoriteUserDataSource
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun openDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        favoriteUserRepository =
            FavoriteUserDataSource(db.favoriteUserDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun favoriteLiveDataTest() = runBlocking(Dispatchers.IO) {
        fun isFavorite() = favoriteUserRepository.getById(mockUser.id).moveToFirst()

        favoriteUserRepository.addToFavorite(mockUser)
        assertEquals(isFavorite(), true)
        favoriteUserRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite(), false)
        favoriteUserRepository.addToFavorite(mockUser)
        assertEquals(isFavorite(), true)
        favoriteUserRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite(), false)


    }

    @Test
    fun doubleTest() = runBlocking(Dispatchers.IO) {
        fun getAllFavorite() = favoriteUserRepository.getAllFavorite().toMultipleUser().toTypedArray()

        assertArrayEquals(getAllFavorite(), emptyArray())

        favoriteUserRepository.addToFavorite(mockUser)
        favoriteUserRepository.addToFavorite(mockUser)
        favoriteUserRepository.addToFavorite(mockUser)

        assertArrayEquals(getAllFavorite(), arrayOf(mockUser))

        favoriteUserRepository.removeFromFavorite(mockUser)
        favoriteUserRepository.removeFromFavorite(mockUser)
        favoriteUserRepository.removeFromFavorite(mockUser)

        assertArrayEquals(getAllFavorite(), emptyArray())
    }
}