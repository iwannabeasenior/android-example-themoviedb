package com.example.model.response

import com.google.gson.annotations.SerializedName

data class ListsResponse(
    @SerializedName("description") val description: String?,
    @SerializedName("favorite_count") val favoriteCount: Int?,
    @SerializedName("id") val id: Int,
    @SerializedName("item_count") val itemCount: Int?,
    @SerializedName("iso_639_1") val iso6391: String?,
    @SerializedName("list_type") val listType: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster_path") val posterPath: String?
)