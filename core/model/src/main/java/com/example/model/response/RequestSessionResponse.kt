package com.example.model.response

import com.google.gson.annotations.SerializedName

data class RequestSessionResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionID: String?
)