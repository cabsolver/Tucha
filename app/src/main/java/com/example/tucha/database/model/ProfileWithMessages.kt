package com.example.tucha.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithMessages(
    @Embedded val profile: DatabaseProfile,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val messages: List<DatabaseMessage>
)

//fun ProfileWithMessages.asDomainModel(): DomainMessage {
//    return DomainMessage(
//
//    )
//}
