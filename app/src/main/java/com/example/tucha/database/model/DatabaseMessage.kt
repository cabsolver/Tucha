package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.tucha.domain.DomainMessage

@Entity(tableName = "messages", primaryKeys = ["id", "user_id"])
data class DatabaseMessage(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "out") val out: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "text") val text: String
)

fun List<DatabaseMessage>.asDomainModel(): List<DomainMessage> {
    return map {
        DomainMessage(
            id = it.id,
            out = it.out,
            date = it.date,
            text = it.text
        )
    }
}
