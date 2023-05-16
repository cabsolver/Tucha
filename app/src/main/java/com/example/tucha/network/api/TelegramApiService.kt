package com.example.tucha.network.api

import com.example.tucha.network.model.telegram.TelegramUpdateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TelegramApiService {
    @GET("/bot{token}/getUpdates")
    suspend fun getUpdates(
        @Path("token") token: String
    ): TelegramUpdateResponse
}