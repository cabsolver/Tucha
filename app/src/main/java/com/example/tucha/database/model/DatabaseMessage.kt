package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class DatabaseMessage(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "out") val out: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "text") val text: String
)
