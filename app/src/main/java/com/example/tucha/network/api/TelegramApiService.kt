package com.example.tucha.network.api

import com.example.tucha.network.model.telegram.TelegramSendMessageResponse
import com.example.tucha.network.model.telegram.TelegramUpdateResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TelegramApiService {
    @GET("/bot{token}/getUpdates")
    suspend fun getUpdates(
        @Path("token") token: String
    ): TelegramUpdateResponse

    @POST("/bot{token}/sendMessage")
    suspend fun sendTextMessage(
        @Path("token") token: String,
        @Query("chat_id") chatId: Int,
        @Query("text") text: String
    ): TelegramSendMessageResponse

    @GET("/bot{token}/deleteMessage")
    suspend fun deleteMessage(
        @Path("token") token: String,
        @Query("chat_id") chatId: Int,
        @Query("message_id") messageId: Int
    )

    @GET("/bot{token}/editMessageText")
    suspend fun editTextMessage(
        @Path("token") token: String,
        @Query("chat_id") chatId: Int,
        @Query("message_id") messageId: Int,
        @Query("text") text: String
    )
}

