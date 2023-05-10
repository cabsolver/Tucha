package com.example.tucha.repository

import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainMessage
import com.example.tucha.network.VkApiService
import com.example.tucha.network.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessagesRepository(
    private val localDataSource: TuchaDatabase,
    private val vkRemoteDataSource: VkApiService
) {
    private val token = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"

    fun getMessagesForDialog(dialogId: Int): Flow<List<DomainMessage>> {
        return localDataSource.messageDao()
            .getMessagesForDialog(dialogId)
            .map {
                it.asDomainModel()
            }
    }

    suspend fun refreshMessages(dialogId: Int) {
        withContext(Dispatchers.IO) {
                val historyResponse = vkRemoteDataSource.getHistory(vkVersion, token, dialogId).response
                localDataSource.messageDao().insertAll(historyResponse.items.asDatabaseModel())
        }
    }
}
