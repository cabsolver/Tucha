package com.example.tucha.network.model.telegram

import com.example.tucha.database.model.DatabaseMessage
import com.squareup.moshi.Json

data class TelegramMessage(
    @Json(name = "message_id") val id: Int,
    @Json(name = "from") val user: TelegramUser,
    @Json(name = "date") val date: Long,
    @Json(name = "text") val text: String
)


