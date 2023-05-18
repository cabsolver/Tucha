package com.example.tucha.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tucha.database.model.DatabaseMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DatabaseMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<DatabaseMessage>)

    @Query("DELETE FROM messages WHERE user_id = :dialogId")
    suspend fun deleteDialogHistory(dialogId: Int)

    @Query("DELETE FROM messages WHERE (id = :messageId) AND (user_id = :dialogId)")
    suspend fun deleteMessage(dialogId: Int, messageId: Int)

    @Query("SELECT * FROM messages ORDER BY date DESC")
    fun getAllMessages(): Flow<List<DatabaseMessage>>

    @Query("SELECT * FROM messages WHERE user_id = :dialogId ORDER BY date DESC")
    fun getMessagesForDialog(dialogId: Int): Flow<List<DatabaseMessage>>

}
