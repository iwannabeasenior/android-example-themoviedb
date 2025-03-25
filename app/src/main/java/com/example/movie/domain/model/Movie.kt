package com.example.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val mediaType: String?,
    val adult: Boolean?,
    val originalLanguage: String?,
    val genreIds: List<Int>?,
    val popularity: Double?,
    val releaseDate: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable


