package com.example.movie.data.request

import com.google.gson.annotations.SerializedName

data class CreateListsParams (
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String
): Params()