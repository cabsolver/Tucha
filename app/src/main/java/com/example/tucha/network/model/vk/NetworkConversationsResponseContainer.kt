package com.example.tucha.network.model.vk

import com.squareup.moshi.Json

data class NetworkConversationsResponseContainer(
    @Json(name = "response") val response: NetworkConversationResponse
)