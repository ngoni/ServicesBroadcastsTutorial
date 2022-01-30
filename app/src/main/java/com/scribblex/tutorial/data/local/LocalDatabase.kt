package com.scribblex.tutorial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scribblex.tutorial.data.entities.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    companion object {

        private const val DB_FILE_NAME = "app.db"

        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DB_FILE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}