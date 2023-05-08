package com.example.tucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainDialog
import com.example.tucha.domain.DomainProfile
import com.example.tucha.network.TuchaApi
import com.example.tucha.network.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DialogsRepository(private val database: TuchaDatabase) {
    private val token = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"
    private val extended = 1

    val dialogs: LiveData<List<DomainDialog>> = database.dialogDao().getDialogPreviews()
        .map {
            it.asDomainModel()
        }

    val profiles: LiveData<List<DomainProfile>> = database.profileDao().getProfiles()
        .map {
            it.asDomainModel()
        }


    suspend fun refreshDialogs() {
        withContext(Dispatchers.IO) {
            val response = TuchaApi.vkClient.getDialogs(vkVersion, token, extended).response
            database.dialogDao().insertAll(response.items.asDatabaseModel())
            database.profileDao().insertAll(response.profiles.asDatabaseModel())
            response.profiles.forEach {
                val historyResponse = TuchaApi.vkClient.getHistory(vkVersion, token, it.id).response
                database.messageDao().insertAll(historyResponse.items.asDatabaseModel())
            }
        }
    }

    suspend fun refreshHistory() {
        withContext(Dispatchers.IO) {

        }
    }
}
