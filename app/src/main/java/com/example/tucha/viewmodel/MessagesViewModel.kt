package com.example.tucha.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tucha.domain.DomainDialog
import com.example.tucha.domain.DomainMessage
import com.example.tucha.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException

class MessagesViewModel(
    private val messagesRepository: MessagesRepository,
    var dialog: DomainDialog
) : ViewModel() {

    var messages: Flow<List<DomainMessage>> =
        messagesRepository.getMessagesForDialog(dialog.id)

    fun refreshMessagesFromRepo(count: Int, offset: Int) = viewModelScope.launch {
        try {
            messagesRepository.refreshMessages(dialog.id, dialog.messengerType, count, offset)
        } catch (networkError: IOException) {
            Log.d("DialogTest", networkError.message!!)
        }
    }

    fun sendTextMessage(message: String) = viewModelScope.launch {
        try {
            messagesRepository.sendTextMessage(dialog.id, dialog.messengerType, message)
        } catch (networkError: IOException) {
            Log.d("DialogTest", networkError.message!!)
        }
    }

    class MessagesViewModelFactory(
        private val messagesRepository: MessagesRepository,
        val dialog: DomainDialog
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MessagesViewModel(messagesRepository, dialog) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}