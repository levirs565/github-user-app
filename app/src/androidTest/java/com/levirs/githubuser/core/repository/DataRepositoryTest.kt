package com.levirs.githubuser.core.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.levirs.githubuser.core.model.User
import org.junit.Test

import org.junit.Assert.*

class DataRepositoryTest {

    @Test
    fun getUserDetails() {
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        val repository = DataRepository(ctx)
        assertEquals(repository.getUserList()[0], repository.getUserDetails(0).run {
            User(userName, name, avatar, company)
        })
    }
}