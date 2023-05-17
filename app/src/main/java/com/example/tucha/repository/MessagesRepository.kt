package com.example.tucha.repository

import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.DatabaseMessage
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainMessage
import com.example.tucha.network.api.TelegramApiService
import com.example.tucha.network.api.VkApiService
import com.example.tucha.network.model.telegram.asDatabaseModel
import com.example.tucha.network.model.telegram.asMessageDatabaseModel
import com.example.tucha.network.model.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessagesRepository(
    private val localDataSource: TuchaDatabase,
    private val vkClient: VkApiService,
    private val telegramClient: TelegramApiService
) {
    private val vkToken = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"

    private val telegramToken = "5988331411:AAEdi4rmkLKqSLCcEC1iRcJrFZcSwG_uL_s"

    fun getMessagesForDialog(dialogId: Int): Flow<List<DomainMessage>> {
        return localDataSource.messageDao()
            .getMessagesForDialog(dialogId)
            .map {
                it.asDomainModel()
            }
    }

    suspend fun refreshMessages(dialogId: Int, messengerType: String, count: Int, offset: Int) {
        withContext(Dispatchers.IO) {
            var messages = listOf<DatabaseMessage>()
            when(messengerType) {
                "telegram" -> {
                    val telegramUpdateResponse = telegramClient.getUpdates(telegramToken)
                    messages = telegramUpdateResponse.updates.asMessageDatabaseModel()
                }
                "vk" -> {
                    val vkHistoryResponse = vkClient
                        .getHistory(vkVersion, vkToken, dialogId, count, offset).response
                    messages = vkHistoryResponse.items.asDatabaseModel()
                }
            }
            localDataSource.messageDao().insertAll(messages)
        }
    }

    suspend fun sendTextMessage(dialogId: Int, messengerType: String, text: String) {
        when(messengerType) {
            "telegram" -> {
                withContext(Dispatchers.IO) {
                    val message = telegramClient
                        .sendTextMessage(telegramToken, dialogId, text).message
                        .asDatabaseModel(1)
                    localDataSource.messageDao().insert(message)
                    localDataSource.dialogDao().update(dialogId, message.text, message.date)
                }
            }
            "vk" -> {
                withContext(Dispatchers.IO) {
                    vkClient.sendTextMessage(vkVersion, vkToken, dialogId, message = text)
                }
            }
        }

    }
}
