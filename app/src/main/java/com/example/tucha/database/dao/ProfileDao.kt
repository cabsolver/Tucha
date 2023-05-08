package com.example.tucha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tucha.database.model.DatabaseProfile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: DatabaseProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<DatabaseProfile>)

    @Query("SELECT * FROM profiles ORDER BY last_name ASC")
    fun getProfiles(): LiveData<List<DatabaseProfile>>


}