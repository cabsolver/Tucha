package com.example.tucha.network.model.vk

import com.example.tucha.database.model.DatabaseDialog
import com.squareup.moshi.Json

data class NetworkDialog(
    @Json(name = "important") val important: Boolean = false,
    @Json(name = "last_message_id") val lastMessageId: Int = 0,
    @Json(name = "in_read") val inRead: Int = 0,
    @Json(name = "out_read") val outRead: Int = 0,
    @Json(name = "peer") val peer: NetworkPeer,
    @Json(name = "unread_count") val unreadCount: Int?
)

fun List<NetworkDialogContainer>.asDatabaseModel(): List<DatabaseDialog> {
    return map {
        DatabaseDialog(
            id = it.dialog.peer.id,
            userId = it.dialog.peer.id,
            unreadCount = it.dialog.unreadCount,
            lastMessageText = it.lastMessage.text,
            lastMessageDate = it.lastMessage.date,
            messengerType = "vk"
        )
    }
}