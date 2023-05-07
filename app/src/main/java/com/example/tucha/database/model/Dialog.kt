package com.example.tucha.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tucha.domain.DomainDialog

data class Dialog(
    @Embedded val profile: DatabaseProfile,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id",
    )
    val messages: List<DatabaseMessage>
)

fun List<Dialog>.asDomainModel(): List<DomainDialog> {
    return map {
        DomainDialog(
            name = it.profile.firstName + " " + it.profile.lastName,
            date = it.messages.last().date,
            lastMessage = it.messages.last().text
        )
    }
}
