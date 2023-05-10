package com.example.tucha.repository

import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainDialog
import com.example.tucha.network.VkApiService
import com.example.tucha.network.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DialogsRepository(
    private val localDataSource: TuchaDatabase,
    private val vkClient: VkApiService
    ) {
    private val token = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"
    private val extended = 1

    val dialogs: Flow<List<DomainDialog>> = localDataSource.dialogDao()
        .getDialogPreviews()
        .map {
            it.asDomainModel()
        }


    suspend fun refreshDialogs() {
        withContext(Dispatchers.IO) {
            val response = vkClient.getDialogs(vkVersion, token, extended).response
            localDataSource.dialogDao().insertAll(response.items.asDatabaseModel())
            localDataSource.profileDao().insertAll(response.profiles.asDatabaseModel())
        }
    }
}
