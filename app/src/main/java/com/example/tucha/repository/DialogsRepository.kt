package com.example.tucha.repository

import com.example.tucha.database.TuchaDatabase
import com.example.tucha.database.model.asDomainModel
import com.example.tucha.domain.DomainDialog
import com.example.tucha.network.api.TelegramApiService
import com.example.tucha.network.api.VkApiService
import com.example.tucha.network.model.telegram.asDialogDatabaseModel
import com.example.tucha.network.model.telegram.asProfileDatabaseModel
import com.example.tucha.network.model.vk.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DialogsRepository(
    private val localDataSource: TuchaDatabase,
    private val vkClient: VkApiService,
    private val telegramClient: TelegramApiService
    ) {
    private val vkToken = "vk1.a.EgxnYhyxvr8p9eOG6H9RARgehyl-UBxhLfnawrLS5veyBXdtSEeq-Lt6bFx_BTZNzDlZ3-5EQfMrWL5FHAwd7iRAuo-bbyHDnNfk7r2gWqs2TVEIT3Y6N88DJjHhogjg72rJ-Ohq6LBgwLTAofLNQXYytv8GabmWT3ilpuebbjbcgoVm3Y3EmiJI5S7K3UGcbqt5Mq6AwP3fpJ2E5L9Ghw"
    private val vkVersion = "5.131"

    private val telegramToken = "5988331411:AAEdi4rmkLKqSLCcEC1iRcJrFZcSwG_uL_s"

    val dialogs: Flow<List<DomainDialog>> = localDataSource.dialogDao()
        .getDialogPreviews()
        .map {
            it.asDomainModel()
        }

    suspend fun refreshDialogs() {
        withContext(Dispatchers.IO) {
            val vkResponse = vkClient.getExtendedDialogs(vkVersion, vkToken).response
            val telegramResponse = telegramClient.getUpdates(telegramToken)

            val vkDialogsAsDatabase = vkResponse.items.asDatabaseModel()
            val telegramDialogsAsDatabase = telegramResponse.updates.asDialogDatabaseModel()

            val vkProfilesAsDatabase = vkResponse.profiles.asDatabaseModel()
            val telegramProfilesAsDatabase = telegramResponse.updates.asProfileDatabaseModel()

            val dialogs = vkDialogsAsDatabase + telegramDialogsAsDatabase
            val profiles = vkProfilesAsDatabase + telegramProfilesAsDatabase

            localDataSource.dialogDao().insertAll(dialogs)
            localDataSource.profileDao().insertAll(profiles)
        }
    }


}
