package com.example.model.request

import com.google.gson.annotations.SerializedName

data class LogoutParams (
    @SerializedName("access_token") val access_token: String
): Params()