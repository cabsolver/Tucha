package com.example.tucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainMessage
import com.example.tucha.network.TuchaApi
import com.example.tucha.network.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessagesRepository(private val database: TuchaDatabase) {
    private val token = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"

    fun getMessages(userId: Int): LiveData<List<DomainMessage>> {
        return database.messageDao().getMessagesForDialog(userId)
            .map {
                it.asDomainModel()
            }
    }

    suspend fun refreshMessages(userId: Int) {
        withContext(Dispatchers.IO) {
                val historyResponse = TuchaApi.vkClient.getHistory(vkVersion, token, userId).response
                database.messageDao().insertAll(historyResponse.items.asDatabaseModel())
        }
    }
}
