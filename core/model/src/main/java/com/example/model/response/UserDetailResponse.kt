package com.example.model.response

import com.google.gson.annotations.SerializedName


data class UserDetailResponse(
    @SerializedName("avatar") val avatar: AvatarResponse?,
    @SerializedName("id") val id: Int,
    @SerializedName("iso_639_1") val iso6391: String?,
    @SerializedName("iso_3166_1") val iso31661: String?,
    @SerializedName("name") val name: String,
    @SerializedName("include_adult") val includeAdult: Boolean?,
    @SerializedName("username") val username: String
)
data class AvatarResponse(
    @SerializedName("gravatar") val gravatar: GravatarResponse?,
    @SerializedName("tmdb") val tmdb: TmdbResponse?
)

data class GravatarResponse(
    @SerializedName("hash") val hash: String?
)

data class TmdbResponse(
    @SerializedName("avatar_path") val avatarPath: String?
)
