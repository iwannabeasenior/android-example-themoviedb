package com.example.model.request

import com.google.gson.annotations.SerializedName

data class V4RequestTokenParams (
    @SerializedName("redirect_to") val redirect_to: String
): Params()