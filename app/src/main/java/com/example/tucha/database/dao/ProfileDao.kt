package com.example.tucha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tucha.database.model.DatabaseProfile
import com.example.tucha.database.model.Dialog

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: DatabaseProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<DatabaseProfile>)

    @Query("SELECT * FROM profiles ORDER BY last_name ASC")
    fun getProfiles(): LiveData<List<DatabaseProfile>>

    @Transaction
    @Query("SELECT * FROM profiles")
    fun getDialogs(): LiveData<List<Dialog>>
}