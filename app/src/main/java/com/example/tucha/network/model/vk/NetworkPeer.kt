package com.example.tucha.network.model.vk

import com.squareup.moshi.Json

data class NetworkPeer(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "type") val type: String = ""
)