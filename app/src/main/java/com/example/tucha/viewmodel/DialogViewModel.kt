package com.example.tucha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tucha.TuchaApplication
import com.example.tucha.database.TuchaDatabase
import com.example.tucha.repository.DialogsRepository
import com.example.tucha.repository.MessagesRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DialogViewModel(application: TuchaApplication) : ViewModel() {

    private val database = TuchaDatabase.getDatabase(application)

    private val dialogsRepository = DialogsRepository(database)
    private val messagesRepository = MessagesRepository(database)

    val dialogs = dialogsRepository.dialogs
//    var currentDialog: DomainDialog? = dialogs.value?.first()
    val messages = messagesRepository.getMessages(173483315)


    init {
        refreshDialogsFromRepo()
    }

    fun refreshDialogsFromRepo() = viewModelScope.launch {
        try {
            dialogsRepository.refreshDialogs()
        } catch (networkError: IOException) {
            TODO()
        }
    }

    fun refreshMessagesFromRepo() = viewModelScope.launch {
        try {
            messagesRepository.refreshMessages(173483315)
        } catch (networkError: IOException) {
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