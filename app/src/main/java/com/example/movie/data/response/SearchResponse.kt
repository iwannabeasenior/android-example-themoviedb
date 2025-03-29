package com.example.movie.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class SearchResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<SearchResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)


open class SearchResult() {}

data class PersonSearchResult(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("media_type") val mediaType: String,
//        @SerializedName("adult") val adult: Boolean,
//        @SerializedName("popularity") val popularity: Double,
//        @SerializedName("gender") val gender: Int,
//        @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("known_for") val knownFor: List<MovieSearchResult>
) : SearchResult()

data class MovieSearchResult(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
//        @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("media_type") val mediaType: String,
//        @SerializedName("adult") val adult: Boolean,
//        @SerializedName("original_language") val originalLanguage: String,
//        @SerializedName("genre_ids") val genreIds: List<Int>,
//        @SerializedName("popularity") val popularity: Double,
//        @SerializedName("release_date") val releaseDate: String?,
//        @SerializedName("video") val video: Boolean,
//        @SerializedName("vote_average") val voteAverage: Double,
//        @SerializedName("vote_count") val voteCount: Int
) : SearchResult()

data class TVSearchResult(
//        @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("media_type") val mediaType: String,
//        @SerializedName("adult") val adult: Boolean,
//        @SerializedName("original_language") val originalLanguage: String,
//        @SerializedName("genre_ids") val genreIds: List<Int>,
//        @SerializedName("popularity") val popularity: Double,
//        @SerializedName("first_air_date") val firstAirDate: String?,
//        @SerializedName("vote_average") val voteAverage: Double,
//        @SerializedName("vote_count") val voteCount: Int,
//        @SerializedName("origin_country") val originCountry: List<String>
) : SearchResult()

//sealed class SearchResult {
//
//}
