package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tucha.domain.DomainMessage

@Entity(tableName = "messages")
data class DatabaseMessage(
    @PrimaryKey(autoGenerate = true) val id: Int,
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
