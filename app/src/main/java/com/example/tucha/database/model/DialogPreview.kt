package com.example.tucha.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tucha.domain.DomainDialog

data class DialogPreview(
    @Embedded val dialog: DatabaseDialog,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    val user: DatabaseProfile,
    @Relation(
        parentColumn = "last_message_id",
        entityColumn = "id",
    )
    val lastMessage: DatabaseMessage?
)

fun List<DialogPreview>.asDomainModel(): List<DomainDialog> {
    return map {
        DomainDialog(
            name = it.user.firstName + " " + it.user.lastName,
            lastMessage = it.lastMessage?.text ?: "",
            date = it.lastMessage?.date ?: 0,
            unread = it.dialog.unreadCount,
            photoUrl = it.user.photoUrl
        )
    }
}
