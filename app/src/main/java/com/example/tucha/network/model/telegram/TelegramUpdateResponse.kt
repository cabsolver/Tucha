package com.example.tucha.network.model.telegram

import com.squareup.moshi.Json

data class TelegramUpdateResponse(
    @Json(name = "result") val updates: List<TelegramUpdate>
)
