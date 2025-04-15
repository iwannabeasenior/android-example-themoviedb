package com.example.model.response

import com.google.gson.annotations.SerializedName

data class ItemStatusResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("item_present") val itemPresent: Boolean
)