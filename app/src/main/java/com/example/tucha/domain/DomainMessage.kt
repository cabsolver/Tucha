package com.example.tucha.domain

import java.text.SimpleDateFormat
import java.util.Locale

data class DomainMessage(
    val id: Int,
    val out: Int,
    val date: Long,
    val text: String
    ) {


    val formattedTimestamp: String
        get() {
            val formatter = SimpleDateFormat("HH:mm", Locale.US)
            return formatter.format(date * 1000)
        }

    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("MMM d", Locale.US)
            return formatter.format(date*1000)
        }
}
