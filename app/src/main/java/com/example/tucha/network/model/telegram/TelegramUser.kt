package com.example.tucha.network.model.telegram

import com.squareup.moshi.Json

data class TelegramUser(
    @Json(name = "id") val id: Int,
    @Json(name = "first_name") val firstName: String
)
