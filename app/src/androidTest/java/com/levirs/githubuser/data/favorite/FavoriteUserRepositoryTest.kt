package com.levirs.githubuser.data.favorite

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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

class FavoriteUserRepositoryTest {
    private val mockUser = User(
        id = 1,
        userName = "tai",
        avatar = "blank"
    )
    private lateinit var favoriteUserRepository: FavoriteUserRepository
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun openDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        favoriteUserRepository =
            FavoriteUserRepository(db.favoriteUserDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun favoriteLiveDataTest() = runBlocking(Dispatchers.IO) {
        val isFavorite = favoriteUserRepository.isFavorite(mockUser)

        favoriteUserRepository.addToFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), true)
        favoriteUserRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), false)
        favoriteUserRepository.addToFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), true)
        favoriteUserRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), false)


    }

    @Test
    fun doubleTest() = runBlocking(Dispatchers.IO) {
        val liveData = favoriteUserRepository.getAllFavorite()

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), emptyArray())

        favoriteUserRepository.addToFavorite(mockUser)
        favoriteUserRepository.addToFavorite(mockUser)
        favoriteUserRepository.addToFavorite(mockUser)

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), arrayOf(mockUser))

        favoriteUserRepository.removeFromFavorite(mockUser)
        favoriteUserRepository.removeFromFavorite(mockUser)
        favoriteUserRepository.removeFromFavorite(mockUser)

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), emptyArray())
    }
}