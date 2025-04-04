package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class MovieAccountStateResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean?,
    @SerializedName("rated") val rated: RatedMovieAccountStateResponse?,
    @SerializedName("rated") val notRated: Boolean?,
    @SerializedName("watchlist") val watchlist: Boolean?
)

data class RatedMovieAccountStateResponse (
    @SerializedName("value") val value: Int
)