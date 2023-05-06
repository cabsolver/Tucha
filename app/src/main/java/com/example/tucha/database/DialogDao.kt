package com.example.tucha.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tucha.database.model.DatabaseDialog

@Dao
interface DialogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dialog: DatabaseDialog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dialogs: List<DatabaseDialog>)

    @Query("SELECT * from dialog ORDER BY id ASC")
    fun getDialogs(): LiveData<List<DatabaseDialog>>
}