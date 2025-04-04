package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class V4RequestTokenResponse (
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("status_code") val statusCode: Int,
)