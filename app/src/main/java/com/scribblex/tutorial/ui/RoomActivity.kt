package com.scribblex.tutorial.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.scribblex.tutorial.R
import com.scribblex.tutorial.data.entities.User
import com.scribblex.tutorial.data.local.LocalDatabase
import com.scribblex.tutorial.data.repository.LocalDatabaseRepository
import kotlinx.coroutines.launch

private const val TAG = "RoomActivity"

class RoomActivity : AppCompatActivity() {

    private val localDatabaseRepository: LocalDatabaseRepository by lazy {
        val usersDao = LocalDatabase.getDatabase(this).getUserDao()
        LocalDatabaseRepository(usersDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_activity)

        lifecycleScope.launch {
            whenStarted {
                val user = User(1, "James")
                val user2 = User(2, "Fred")
                localDatabaseRepository.insertUsers(listOf(user, user2))
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        lifecycleScope.launch {
            whenStarted {
                val users = localDatabaseRepository.getAllUser()
                Log.d(TAG, "Number of users: ${users.size}")
                Log.d(TAG, "Users: $users")
            }
        }
    }

}