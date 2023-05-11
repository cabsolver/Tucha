package com.example.tucha.network.model.vk

import com.example.tucha.database.model.DatabaseMessage
import com.squareup.moshi.Json

data class NetworkMessage(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "from_id") val fromId: Int = 0,
    @Json(name = "peer_id") val peerId: Int,
    @Json(name = "out") val out: Int = 0,
    @Json(name = "date") val date: Long = 0,
    @Json(name = "text") val text: String = "",
    @Json(name = "is_hidden") val isHidden: Boolean = false,
    @Json(name = "important") val isImportant: Boolean = false,
)

fun List<NetworkMessage>.asDatabaseModel(): List<DatabaseMessage> {
    return map {
        DatabaseMessage(
            id = it.id,
            userId = it.peerId,
            out = it.out,
            date = it.date,
            text = it.text
        )
    }
}