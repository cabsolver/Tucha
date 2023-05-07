package com.example.tucha.network.vk

import com.example.tucha.database.model.DatabaseProfile
import com.squareup.moshi.Json

data class NetworkProfile(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "last_name") val lastName: String = "",
    @Json(name = "first_name") val firstName: String = "",
    @Json(name = "sex") val sex: Int = 0,
    @Json(name = "photo_50") val photoUrl: String = "",
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