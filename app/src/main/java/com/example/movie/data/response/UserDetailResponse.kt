package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponse(
    @SerialName("avatar") val avatar: AvatarResponse?,
    @SerialName("id") val id: Int,
    @SerialName("iso_639_1") val iso6391: String?,
    @SerialName("iso_3166_1") val iso31661: String?,
    @SerialName("name") val name: String,
    @SerialName("include_adult") val includeAdult: Boolean?,
    @SerialName("username") val username: String
)

@Serializable
data class AvatarResponse(
    @SerialName("gravatar") val gravatar: GravatarResponse?,
    @SerialName("tmdb") val tmdb: TmdbResponse?
)

@Serializable
data class GravatarResponse(
    @SerialName("hash") val hash: String?
)

@Serializable
data class TmdbResponse(
    @SerialName("avatar_path") val avatarPath: String?
)
