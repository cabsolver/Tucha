package com.example.tucha.network.model.vk

import com.squareup.moshi.Json

data class NetworkHistoryResponse(
    @Json(name = "count") val count: Int = 0,
    @Json(name = "items") val items: List<NetworkMessage>
)