package com.example.tucha.network

import com.example.tucha.network.vk.NetworkConversationsResponseContainer
import com.example.tucha.network.vk.NetworkHistoryResponseContainer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val VK_BASE_URL = "https://api.vk.com/method/"
private const val TELEGRAM_BASE_URL = "https://api.telegram.org/bot"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object TuchaApi {
    val vkClient by lazy {
        getRetrofitService(VK_BASE_URL).create(VkApiService::class.java)
    }

    val telegramClient by lazy {
        getRetrofitService(TELEGRAM_BASE_URL).create(TelegramApiService::class.java)
    }

    private fun getRetrofitService(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }
}

interface VkApiService {
    @GET("messages.getConversations")
    suspend fun getDialogs(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("extended") extended: Int?
    ): NetworkConversationsResponseContainer

    @GET("messages.getHistory")
    suspend fun getHistory(
        @Query("v") version: String,
        @Query("access_token") token: String,
        @Query("user_id") userId: Int
    ): NetworkHistoryResponseContainer
}

interface TelegramApiService {

}