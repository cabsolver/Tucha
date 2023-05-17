package com.example.tucha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tucha.database.model.DatabaseDialog
import com.example.tucha.database.model.DialogPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface DialogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dialog: DatabaseDialog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dialogs: List<DatabaseDialog>)

    @Query("""UPDATE dialogs
        SET last_message_text = :text, last_message_date = :date
        WHERE user_id = :dialogId""")
    suspend fun update(dialogId: Int, text: String, date: Long)

    @Query("SELECT * from dialogs ORDER BY id ASC")
    fun getDialogs(): LiveData<List<DatabaseDialog>>

    @Transaction
    @Query("SELECT * FROM dialogs ORDER BY last_message_date DESC")
    fun getDialogPreviews(): Flow<List<DialogPreview>>
}