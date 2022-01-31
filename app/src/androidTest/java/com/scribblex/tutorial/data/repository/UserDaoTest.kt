package com.scribblex.tutorial.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.scribblex.tutorial.data.local.LocalDatabase
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: LocalDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
            .allowMainThreadQueries() // allowing main thread queries, just for testing
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetUsersWhenNoUsersInserted() {
        val users = database.getUserDao().getAllUsers()
        Assert.assertEquals(0, users.size)
    }

}