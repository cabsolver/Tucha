package com.example.tucha.network.vk

import com.squareup.moshi.Json

data class NetworkHistoryResponseContainer(
    @Json(name = "response") val response: NetworkHistoryResponse
)