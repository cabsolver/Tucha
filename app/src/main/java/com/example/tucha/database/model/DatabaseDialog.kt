package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tucha.domain.DomainDialog

@Entity(tableName = "dialogs")
data class DatabaseDialog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "last_message") val lastMessage: String
)

fun List<DatabaseDialog>.asDomainModel(): List<DomainDialog> {
    return map {
        DomainDialog(
            id = it.id,
            lastMessage = it.lastMessage
        )
    }
}
