package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class ListsDetailResponse (
    @SerializedName("created_by") val createdBy: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("favorite_count") val favoriteCount: Int?,
    @SerializedName("id") val id: Int,
    @SerializedName("items") val items: List<MovieResponse>?,
    @SerializedName("item_count") val itemCount: Int?,
    @SerializedName("ios_639_1") val iso_639_1: String?,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String?
)