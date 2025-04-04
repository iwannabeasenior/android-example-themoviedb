package com.example.movie.domain.model

import com.example.movie.data.response.RatedMovieAccountStateResponse
import com.google.gson.annotations.SerializedName

data class MovieAccountState (
    val id: Int,
    val favorite: Boolean?,
    val rated: Int?,
    val watchlist: Boolean?
)