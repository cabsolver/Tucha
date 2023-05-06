package com.example.tucha.network.vk

import com.squareup.moshi.Json

data class NetworkDialogContainer(
    @Json(name = "last_message") val lastMessage: NetworkMessage,
    @Json(name = "conversation") val dialog: NetworkDialog
)