package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dialogs")
data class DatabaseDialog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "unread_count") val unreadCount: Int?,
    @ColumnInfo(name = "last_message_text") val lastMessageText: String,
    @ColumnInfo(name = "last_message_date") val lastMessageDate: Long,
    @ColumnInfo(name = "messenger_type") val messengerType: String
)
