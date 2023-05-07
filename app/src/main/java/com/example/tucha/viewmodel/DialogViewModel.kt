package com.example.tucha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tucha.TuchaApplication
import com.example.tucha.database.TuchaDatabase
import com.example.tucha.repository.DialogsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DialogViewModel(application: TuchaApplication) : ViewModel() {

    private val dialogsRepository = DialogsRepository(TuchaDatabase.getDatabase(application))
    val dialogs = dialogsRepository.dialogs

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() = viewModelScope.launch {
        try {
            dialogsRepository.refreshDialogs()
        } catch (networkError: IOException) {
            if (dialogs.value.isNullOrEmpty())
                TODO()
        }
    }

    class DialogViewModelFactory(val app: TuchaApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DialogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DialogViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}