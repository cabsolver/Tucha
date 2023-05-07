package com.example.tucha.network.vk

import com.squareup.moshi.Json

data class NetworkConversationsResponseContainer(
    @Json(name = "response") val response: NetworkConversationResponse
)