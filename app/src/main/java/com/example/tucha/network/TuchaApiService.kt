package com.example.tucha.network

import com.example.tucha.network.api.TelegramApiService
import com.example.tucha.network.api.VkApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val VK_BASE_URL = "https://api.vk.com/method/"
private const val TELEGRAM_BASE_URL = "https://api.telegram.org/"
const val NO_PHOTO_URL = "https://t4.ftcdn.net/jpg/00/65/77/27/240_F_65772719_A1UV5kLi5nCEWI0BNLLiFaBPEkUbv5Fv.jpg"

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