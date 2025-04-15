package com.example.model.request

import com.google.gson.annotations.SerializedName

data class RequestSessionParams (
    @SerializedName("request_token") val request_token: String
) : Params()


