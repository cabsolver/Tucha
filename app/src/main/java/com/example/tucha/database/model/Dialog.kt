package com.example.tucha.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class Dialog(
    @Embedded val profile: DatabaseProfile,
    @Relation(
        parentColumn = "id",
        entityColumn = "sender_id",
    )
    val messages: List<DatabaseMessage>
)
