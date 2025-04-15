package com.example.model.request

import com.google.gson.annotations.SerializedName

data class AddRatingParams (
    @SerializedName("value") val value: Int
): Params()