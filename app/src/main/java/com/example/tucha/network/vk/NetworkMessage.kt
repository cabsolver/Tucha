package com.example.tucha.network.vk

import com.example.tucha.database.model.DatabaseMessage
import com.squareup.moshi.Json

data class NetworkMessage(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "from_id") val fromId: Int = 0,
    @Json(name = "out") val out: Int = 0,
    @Json(name = "date") val date: Int = 0,
    @Json(name = "text") val text: String = "",
    @Json(name = "fwd_messages") val forwards: List<NetworkMessage> = listOf(),
    @Json(name = "is_hidden") val isHidden: Boolean = false,
    @Json(name = "important") val isImportant: Boolean = false,
)

fun List<NetworkMessage>.asDatabaseModel(): List<DatabaseMessage> {
    return map {
        DatabaseMessage(
            id = it.id,
            senderId = it.fromId,
            date = it.date,
            text = it.text
        )
    }
}