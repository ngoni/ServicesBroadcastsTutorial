package com.scribblex.tutorial.data.repository

import com.scribblex.tutorial.data.entities.User
import com.scribblex.tutorial.data.local.UsersDao

class LocalDatabaseRepository(private val usersDao: UsersDao) {

    fun getAllUser() = usersDao.getAllUsers()

    fun insertUsers(users: List<User>) = usersDao.insertUser(users)

    fun deleteUser(user: User) = usersDao.deleteUser(user)

    fun updateUser(user: User) = usersDao.updateUser(user)

}