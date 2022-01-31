package com.scribblex.tutorial.data.repository

import com.scribblex.tutorial.data.entities.User
import com.scribblex.tutorial.data.local.UsersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDatabaseRepository(private val usersDao: UsersDao) {

    suspend fun getAllUser() = withContext(Dispatchers.IO) {
        usersDao.getAllUsers()
    }

    suspend fun insertUsers(users: List<User>) = withContext(Dispatchers.IO) {
        usersDao.insertUser(users)
    }

    suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        usersDao.deleteUser(user)
    }

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        usersDao.updateUser(user)
    }

    suspend fun getUser(userId: Int) = withContext(Dispatchers.IO) {
        usersDao.getUser(userId)
    }

}