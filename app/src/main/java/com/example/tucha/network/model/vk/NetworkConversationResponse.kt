package com.example.tucha.network.model.vk

import com.squareup.moshi.Json

data class NetworkConversationResponse(
    @Json(name = "count") val count: Int = 0,
    @Json(name = "profiles") val profiles: List<NetworkProfile>,
    @Json(name = "items") val items: List<NetworkDialogContainer>
)