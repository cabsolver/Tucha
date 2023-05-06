package com.example.tucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainDialog

class DialogsRepository(private val database: TuchaDatabase) {
    val dialogs: LiveData<List<DomainDialog>> = database.dialogDao().getDialogs()
        .map {
            it.asDomainModel()
        }

    suspend fun refreshDialogs() {

    }
}
