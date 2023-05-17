package com.example.tucha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tucha.database.dao.DialogDao
import com.example.tucha.database.dao.MessageDao
import com.example.tucha.database.dao.ProfileDao
import com.example.tucha.database.model.DatabaseDialog
import com.example.tucha.database.model.DatabaseMessage
import com.example.tucha.database.model.DatabaseProfile

@Database(
    entities = [
        DatabaseDialog::class,
        DatabaseProfile::class,
        DatabaseMessage::class
    ],
    version = 12,
    exportSchema = false
)
abstract class TuchaDatabase : RoomDatabase() {

    abstract fun dialogDao(): DialogDao
    abstract fun profileDao(): ProfileDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: TuchaDatabase? = null

        fun getDatabase(context: Context): TuchaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TuchaDatabase::class.java,
                    "tucha_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}