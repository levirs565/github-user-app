package com.levirs.githubuser.core.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.levirs.githubuser.core.database.AppDatabase
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.util.getOrAwaitValue
import com.levirs.githubuser.util.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserFavoriteRepositoryTest {
    private val mockUser = User(id = 1, userName = "tai", avatar = "blank")
    private lateinit var userFavoriteRepository: UserFavoriteRepository
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun openDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userFavoriteRepository = UserFavoriteRepository(db.userFavoriteDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun favoriteLiveDataTest() = runBlocking(Dispatchers.IO) {
        val isFavorite = userFavoriteRepository.isFavorite(mockUser)

        userFavoriteRepository.addToFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), true)
        userFavoriteRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), false)
        userFavoriteRepository.addToFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), true)
        userFavoriteRepository.removeFromFavorite(mockUser)
        assertEquals(isFavorite.getOrAwaitValue(), false)


    }

    @Test
    fun doubleTest() = runBlocking(Dispatchers.IO) {
        val liveData = userFavoriteRepository.getAllFavorite()

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), emptyArray())

        userFavoriteRepository.addToFavorite(mockUser)
        userFavoriteRepository.addToFavorite(mockUser)
        userFavoriteRepository.addToFavorite(mockUser)

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), arrayOf(mockUser))

        userFavoriteRepository.removeFromFavorite(mockUser)
        userFavoriteRepository.removeFromFavorite(mockUser)
        userFavoriteRepository.removeFromFavorite(mockUser)

        assertArrayEquals(liveData.getOrAwaitValue().toTypedArray(), emptyArray())
    }
}