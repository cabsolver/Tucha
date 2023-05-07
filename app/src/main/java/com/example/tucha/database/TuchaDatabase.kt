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
    version = 3,
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
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TuchaDatabase::class.java,
                    "item_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}