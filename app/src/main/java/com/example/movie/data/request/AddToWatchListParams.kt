package com.example.movie.data.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import okhttp3.MediaType

data class AddToWatchListParams(
    @SerializedName("media_type") val media_type: String,
    @SerializedName("media_id") val media_id: Int,
    @SerializedName("watch_list") val watch_list: Boolean
): Params()