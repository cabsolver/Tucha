package com.example.tucha.network.model.telegram

import com.squareup.moshi.Json

data class TelegramSendMessageResponse(
    @Json(name = "result") val message: TelegramMessage
)
