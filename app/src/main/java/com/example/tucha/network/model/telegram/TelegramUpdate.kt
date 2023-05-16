package com.example.tucha.network.model.telegram

import com.example.tucha.database.model.DatabaseDialog
import com.example.tucha.database.model.DatabaseMessage
import com.example.tucha.database.model.DatabaseProfile
import com.squareup.moshi.Json

data class TelegramUpdate(
    @Json(name = "message") val message: TelegramMessage
)

fun List<TelegramUpdate>.asDialogDatabaseModel(): List<DatabaseDialog> {
    return map {
        DatabaseDialog(
            id = it.message.user.id,
            userId = it.message.user.id,
            unreadCount = null,
            lastMessageText = it.message.text,
            lastMessageDate = it.message.date,
            messengerType = "telegram"
        )
    }
}

fun List<TelegramUpdate>.asProfileDatabaseModel(): List<DatabaseProfile> {
    return map {
        DatabaseProfile(
            id = it.message.user.id,
            firstName = it.message.user.firstName
        )
    }
}

fun List<TelegramUpdate>.asMessageDatabaseModel(): List<DatabaseMessage> {
    return map {
        DatabaseMessage(
            id = it.message.id,
            userId = it.message.user.id,
            out = 0,
            date = it.message.date,
            text = it.message.text
        )
    }
}