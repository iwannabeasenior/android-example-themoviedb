package com.example.model.response

import com.google.gson.annotations.SerializedName

data class MovieAccountStateResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean?,
    @SerializedName("rated") val rated: RatedMovieAccountStateResponse?,
    @SerializedName("watchlist") val watchlist: Boolean?
)

data class RatedMovieAccountStateResponse (
    @SerializedName("value") val value: Int
)