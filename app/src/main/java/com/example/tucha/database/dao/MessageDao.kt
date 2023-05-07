package com.example.tucha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tucha.database.model.DatabaseMessage

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DatabaseMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<DatabaseMessage>)

    @Query("SELECT * FROM messages ORDER BY date DESC")
    fun getMessages(): LiveData<List<DatabaseMessage>>
}
