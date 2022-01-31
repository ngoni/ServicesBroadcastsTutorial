package com.scribblex.tutorial.data.local

import androidx.room.*
import com.scribblex.tutorial.data.entities.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM Users WHERE id = :userId ")
    fun getUser(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: List<User>)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}