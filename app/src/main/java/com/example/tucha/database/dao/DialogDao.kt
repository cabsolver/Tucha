package com.example.tucha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tucha.database.model.DatabaseDialog
import com.example.tucha.database.model.DialogPreview

@Dao
interface DialogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dialog: DatabaseDialog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dialogs: List<DatabaseDialog>)

    @Query("SELECT * from dialogs ORDER BY id ASC")
    fun getDialogs(): LiveData<List<DatabaseDialog>>

    @Transaction
    @Query("SELECT * FROM dialogs")
    fun getDialogPreviews(): LiveData<List<DialogPreview>>
}