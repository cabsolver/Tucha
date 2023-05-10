package com.example.tucha.domain

import java.text.SimpleDateFormat
import java.util.Locale

data class DomainDialog(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val date: Long,
    val lastMessage: String,
    val unread: Int?
) {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("MMM d", Locale.US)
            return formatter.format(date*1000)
        }

    companion object {
        fun empty(): DomainDialog {
            return DomainDialog(
                id = 0,
                name = "None",
                photoUrl = "",
                date = 0,
                lastMessage = "None",
                unread = null
            )
        }
    }
}
