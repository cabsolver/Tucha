package com.example.tucha.network.vk

import com.example.tucha.database.model.DatabaseDialog
import com.squareup.moshi.Json

data class NetworkDialog(
    @Json(name = "important") val important: Boolean = false,
    @Json(name = "last_message_id") val lastMessageId: Int = 0,
    @Json(name = "in_read") val inRead: Int = 0,
    @Json(name = "peer") val peer: NetworkPeer,
    @Json(name = "is_marked_unread") val isMarkedUnread: Boolean = false,
    @Json(name = "last_conversation_message_id") val lastConversationMessageId: Int = 0
)

fun List<NetworkDialogContainer>.asDatabaseModel(): List<DatabaseDialog> {
    return map {
        DatabaseDialog(
            id = it.dialog.peer.id,
            lastMessage = it.lastMessage.text
        )
    }
}