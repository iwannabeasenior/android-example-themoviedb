package com.example.movie.data.request

import kotlinx.serialization.SerialName

data class RequestSessionParams (
    @SerialName("request_token") val requestToken: String
)