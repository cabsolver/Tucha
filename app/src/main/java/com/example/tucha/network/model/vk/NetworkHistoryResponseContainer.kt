package com.example.tucha.network.model.vk

import com.squareup.moshi.Json

data class NetworkHistoryResponseContainer(
    @Json(name = "response") val response: NetworkHistoryResponse
)