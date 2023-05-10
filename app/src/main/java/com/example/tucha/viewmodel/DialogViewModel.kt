package com.example.tucha.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tucha.domain.DomainDialog
import com.example.tucha.repository.DialogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException

class DialogViewModel(
    private val dialogsRepository: DialogsRepository
) : ViewModel() {

    val refreshFrequency = 4000L

    val dialogs: Flow<List<DomainDialog>> =
        dialogsRepository.dialogs

    fun refreshDialogsFromRepo() = viewModelScope.launch {
        try {
            dialogsRepository.refreshDialogs()
        } catch (networkError: IOException) {
            Log.d("DialogTest", networkError.message!!)
        }
    }

    class DialogViewModelFactory(
        private val dialogsRepository: DialogsRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DialogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DialogViewModel(dialogsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}