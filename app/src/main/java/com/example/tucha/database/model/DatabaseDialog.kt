package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dialogs")
data class DatabaseDialog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "unread_count") val unreadCount: Int?,
    @ColumnInfo(name = "last_message_id") val lastMessageId: Int
)

//fun List<DatabaseDialog>.asDomainModel(): List<DomainDialog> {
//    return map {
//        DomainDialog(
////            name = it.user.firstName + " " + it.user.lastName,
//            name = it.id.toString(),
//            lastMessage = it.lastMessageId.toString(),
//            date = it.lastMessageId.toLong()
//        )
//    }
//}
