package com.example.tucha.network.api

import com.example.tucha.network.model.vk.NetworkConversationsResponseContainer
import com.example.tucha.network.model.vk.NetworkHistoryResponseContainer
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Random

interface VkApiService {
    @GET("messages.getConversations")
    suspend fun getDialogs(
        @Query("v") version: String,
        @Query("access_token") token: String
    ): NetworkConversationsResponseContainer

    @GET("messages.getConversations")
    suspend fun getExtendedDialogs(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("extended") extended: Int = 1
    ): NetworkConversationsResponseContainer

    @GET("messages.getHistory")
    suspend fun getHistory(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("user_id") userId: Int,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?
    ): NetworkHistoryResponseContainer

    @POST("messages.send")
    suspend fun sendTextMessage(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("user_id") userId: Int,
        @Query("random_id") randomId: Int = Random().nextInt(),
        @Query("message") message: String
    )

    @GET("messages.edit")
    suspend fun editTextMessage(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("peer_id") userId: Int,
        @Query("message_id") messageId: Int,
        @Query("message") message: String
    )

    @GET("messages.delete")
    suspend fun deleteMessageLocally(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("peer_id") chatId: Int,
        @Query("message_ids") messageIds: String
    )

    @GET("messages.delete")
    suspend fun deleteMessageForAll(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("peer_id") chatId: Int,
        @Query("message_ids") messageIds: String,
        @Query("delete_for_all") deleteForAll: Int = 1
    )
}