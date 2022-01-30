package com.scribblex.tutorial.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String? = null
)