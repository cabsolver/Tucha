package com.example.tucha.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class DomainDialog(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val date: Long,
    val lastMessage: String,
    val unread: Int?,
    val messengerType: String
) : Parcelable {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("MMM d", Locale.US)
            return formatter.format(date*1000)
        }

    val formattedTimestamp: String
        get() {
            val formatter = SimpleDateFormat("HH:mm", Locale.US)
            return formatter.format(date * 1000)
        }

//    companion object {
//        fun empty(): DomainDialog {
//            return DomainDialog(
//                id = 0,
//                name = "None",
//                photoUrl = "",
//                date = 0,
//                lastMessage = "None",
//                unread = null
//            )
//        }
//    }
}
