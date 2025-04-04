package com.example.movie.data.request

import kotlinx.serialization.SerialName

data class RequestSessionParams (
    @SerialName("request_token") val request_token: String
) : Params()


