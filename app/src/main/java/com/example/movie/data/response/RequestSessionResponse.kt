package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class RequestSessionResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionID: String?
)