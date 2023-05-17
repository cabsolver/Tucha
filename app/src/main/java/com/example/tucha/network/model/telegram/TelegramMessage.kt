package com.example.tucha.network.model.telegram

import com.example.tucha.database.model.DatabaseMessage
import com.squareup.moshi.Json

data class TelegramMessage(
    @Json(name = "message_id") val id: Int,
    @Json(name = "chat") val user: TelegramUser,
    @Json(name = "date") val date: Long,
    @Json(name = "text") val text: String
)

fun TelegramMessage.asDatabaseModel(out: Int): DatabaseMessage {
    return DatabaseMessage(
        id = id,
        userId = user.id.toInt(),
        out = out,
        date = date,
        text = text
    )
}


