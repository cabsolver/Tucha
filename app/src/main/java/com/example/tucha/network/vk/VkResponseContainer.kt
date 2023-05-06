package com.example.tucha.network.vk

import com.squareup.moshi.Json

data class VkResponseContainer(
    @Json(name = "response") val response: NetworkResponse
)