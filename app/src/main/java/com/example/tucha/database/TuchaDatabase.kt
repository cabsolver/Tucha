package com.example.tucha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tucha.database.model.DatabaseDialog

@Database(entities = [DatabaseDialog::class], version = 1, exportSchema = false)
abstract class TuchaDatabase : RoomDatabase() {

    abstract fun dialogDao(): DialogDao

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