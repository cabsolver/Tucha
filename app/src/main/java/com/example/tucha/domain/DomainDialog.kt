package com.example.tucha.domain

import java.text.SimpleDateFormat
import java.util.Locale

data class DomainDialog(
    val name: String,
    val date: Long,
    val lastMessage: String
) {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("MMM d", Locale.US)
            return formatter.format(date*1000)
        }
}
