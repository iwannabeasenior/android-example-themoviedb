package com.example.movie.data.response

import com.example.movie.data.request.Params
import com.google.gson.annotations.SerializedName

data class ItemStatusResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("item_present") val itemPresent: Boolean
): Params()