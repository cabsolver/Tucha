package com.example.tucha

import android.app.Application
import com.example.tucha.database.TuchaDatabase

class TuchaApplication : Application() {
    val database: TuchaDatabase by lazy { TuchaDatabase.getDatabase(this)}
}