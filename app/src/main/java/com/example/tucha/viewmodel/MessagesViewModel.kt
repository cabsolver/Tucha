package com.example.tucha.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tucha.domain.DomainMessage
import com.example.tucha.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException

class MessagesViewModel(
    private val messagesRepository: MessagesRepository,
    var dialogId: Int
) : ViewModel() {

    var messages: Flow<List<DomainMessage>> =
        messagesRepository.getMessagesForDialog(dialogId)

    init {
    }

    fun refreshMessagesFromRepo(userId: Int) = viewModelScope.launch {
        try {
            messagesRepository.refreshMessages(userId)
        } catch (networkError: IOException) {
            Log.d("DialogTest", networkError.message!!)
        }
    }

    class MessagesViewModelFactory(
        private val messagesRepository: MessagesRepository,
        val dialogId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MessagesViewModel(messagesRepository, dialogId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}