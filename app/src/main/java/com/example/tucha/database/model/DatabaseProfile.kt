package com.example.tucha.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tucha.domain.DomainProfile

@Entity(tableName = "profiles")
data class DatabaseProfile(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String = ""
)

fun List<DatabaseProfile>.asDomainModel(): List<DomainProfile> {
    return map {
        DomainProfile(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            photoUrl = it.photoUrl
        )
    }
}
