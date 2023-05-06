package com.example.tucha.network.vk

import com.squareup.moshi.Json

data class NetworkOnlineInfo(
    @Json(name = "is_online") val isOnline: Boolean = false,
    @Json(name = "is_mobile") val isMobile: Boolean = false,
    @Json(name = "last_seen") val lastSeen: Int = 0,
    @Json(name = "visible") val visible: Boolean = false,
    @Json(name = "app_id") val appId: Int = 0
)