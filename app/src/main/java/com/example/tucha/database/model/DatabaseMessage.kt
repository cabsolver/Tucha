package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class DatabaseMessage(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "sender_id") val senderId: Int,
    @ColumnInfo(name = "date") val date: Int,
    @ColumnInfo(name = "text") val text: String
)
