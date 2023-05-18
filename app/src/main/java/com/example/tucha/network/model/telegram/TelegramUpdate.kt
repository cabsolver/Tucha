package com.example.tucha.network.model.telegram

import com.example.tucha.database.model.DatabaseDialog
import com.example.tucha.database.model.DatabaseMessage
import com.example.tucha.database.model.DatabaseProfile
import com.squareup.moshi.Json

data class TelegramUpdate(
    @Json(name = "message") val message: TelegramMessage?,
    @Json(name = "edited_message") val editedMessage: TelegramMessage?
)

fun List<TelegramUpdate>.asDialogDatabaseModel(): List<DatabaseDialog> {

    var dialogId: Int = 0
    var dialogUserId: Int = 0
    var text: String = ""
    var date: Long = 0

    return map {
        if (it.message != null) {
            dialogId = it.message.user.id.toInt()
            dialogUserId = it.message.user.id.toInt()
            text = it.message.text
            date = it.message.date
        } else if (it.editedMessage != null) {
            dialogId = it.editedMessage.user.id.toInt()
            dialogUserId = it.editedMessage.user.id.toInt()
            text = it.editedMessage.text
            date = it.editedMessage.date
        }
        DatabaseDialog(
            id = dialogId,
            userId = dialogUserId,
            unreadCount = null,
            lastMessageText = "",
            lastMessageDate = date,
            messengerType = "telegram"
        )
    }
}

fun List<TelegramUpdate>.asProfileDatabaseModel(): List<DatabaseProfile> {
    var id = 0
    var firstName = ""
    return map {
        if (it.message != null) {
            id = it.message.user.id.toInt()
            firstName = it.message.user.firstName
        } else if (it.editedMessage != null) {
            id = it.editedMessage.user.id.toInt()
            firstName = it.editedMessage.user.firstName
        }
        DatabaseProfile(
            id = id,
            firstName = firstName
        )
    }
}

fun List<TelegramUpdate>.asMessageDatabaseModel(): List<DatabaseMessage> {
    var messageId: Int = 0
    var userId: Int = 0
    var text: String = ""
    var date: Long = 0

    return map {
        if (it.message != null) {
            messageId = it.message.id
            userId = it.message.user.id.toInt()
            text = it.message.text
            date = it.message.date
        } else if (it.editedMessage != null) {
            messageId = it.editedMessage.id
            userId = it.editedMessage.user.id.toInt()
            text = it.editedMessage.text
            date = it.editedMessage.date
        }
        DatabaseMessage(
            id = messageId,
            userId = userId,
            out = 0,
            date = date,
            text = text
        )
    }
}