package com.example.model.request

import com.google.gson.annotations.SerializedName

data class AddToWatchListParams(
    @SerializedName("media_type") val media_type: String,
    @SerializedName("media_id") val media_id: Int,
    @SerializedName("watchlist") val watchlist: Boolean
): Params()