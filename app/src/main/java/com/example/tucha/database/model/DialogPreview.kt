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
    val user: DatabaseProfile
)

fun List<DialogPreview>.asDomainModel(): List<DomainDialog> {
    return map {
        DomainDialog(
            id = it.dialog.id,
            name = it.user.firstName + " " + it.user.lastName,
            photoUrl = it.user.photoUrl,
            unread = it.dialog.unreadCount,
            lastMessage = it.dialog.lastMessageText,
            date = it.dialog.lastMessageDate,
            messengerType = it.dialog.messengerType
        )
    }
}
