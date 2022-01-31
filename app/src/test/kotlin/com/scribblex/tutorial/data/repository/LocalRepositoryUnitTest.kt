package com.scribblex.tutorial.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.scribblex.tutorial.data.entities.User
import com.scribblex.tutorial.data.local.UsersDao
import kotlinx.coroutines.runBlocking
import okhttp3.internal.immutableListOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalDatabaseRepositoryUnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val usersDao: UsersDao = mock()
    private lateinit var localDatabaseRepository: LocalDatabaseRepository

    @Before
    fun setup() {
        localDatabaseRepository = LocalDatabaseRepository(usersDao)
    }

    @Test
    fun testInsertingUsersCallUserDaoWithRunBlocking(): Unit = runBlocking {
        val usersList = immutableListOf(User(1, "James"), User(2, "Fred"))
        localDatabaseRepository.insertUsers(usersList)
        // verify
        verify(usersDao).insertUser(usersList)
    }

    @Test
    fun testGettingAllUsersWithDao() : Unit = runBlocking {
        val usersList = immutableListOf(User(1, "James"), User(2, "Fred"))
        localDatabaseRepository.insertUsers(usersList)
        val users = localDatabaseRepository.getAllUser()
        Assert.assertEquals(2, users.size)
    }

}