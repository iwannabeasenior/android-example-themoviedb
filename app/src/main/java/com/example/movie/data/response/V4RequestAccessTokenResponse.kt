package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class V4RequestAccessTokenResponse (
    @SerializedName("account_id") val accountId: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("status_code") val statusCode: String,
)