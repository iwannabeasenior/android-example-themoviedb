package com.example.model.model

import com.example.model.response.RatedMovieAccountStateResponse
import com.google.gson.annotations.SerializedName

data class MovieAccountState (
    val id: Int,
    val favorite: Boolean?,
    val rated: Int?,
    val watchlist: Boolean?
)