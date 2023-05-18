package com.example.tucha.network.model.vk

import com.example.tucha.database.model.DatabaseProfile
import com.example.tucha.network.NO_PHOTO_URL
import com.squareup.moshi.Json

data class NetworkProfile(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "last_name") val lastName: String = "",
    @Json(name = "first_name") val firstName: String = "",
    @Json(name = "sex") val sex: Int = 0,
    @Json(name = "photo_100") val photoUrl: String = NO_PHOTO_URL,
    @Json(name = "online_info") val onlineInfo: NetworkOnlineInfo
)

fun List<NetworkProfile>.asDatabaseModel(): List<DatabaseProfile> {
    return map {
        DatabaseProfile(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            photoUrl = it.photoUrl
        )
    }
}