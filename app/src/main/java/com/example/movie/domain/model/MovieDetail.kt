package com.example.movie.domain.model

import com.example.movie.utils.formatTimeDuration
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val adult: Boolean?,
    val backdropPath: String?,
    val belongsToCollection: Boolean?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountriesISO: List<ProductionCountryISO>?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) {
    fun formatVoteAverage() = voteAverage?.let {
        (it * 10).toInt()
    } ?: 0

    fun formatTime() = runtime?.let {
            formatTimeDuration(it)
        } ?: ""
    fun formatGenres() =
        genres?.joinToString(", ") { it.name.toString() } ?: ""
}

@Serializable
data class Genre(
    val id: Int?,
    val name: String?
)

@Serializable
data class ProductionCompany(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?
)

@Serializable
data class ProductionCountryISO(
    val iso31661: String?,
    val name: String?
)

@Serializable
data class SpokenLanguage(
    val englishName: String?,
    val iso6391: String?,
    val name: String?
)
