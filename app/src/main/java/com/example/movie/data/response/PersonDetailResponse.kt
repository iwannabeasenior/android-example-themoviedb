package com.example.movie.data.response

import com.google.gson.annotations.SerializedName

data class PersonDetailResponse(
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("biography") val biography: String?,
    @SerializedName("known_for_department") val knownFor: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("also_known_as") val alsoKnownAs: List<String>?, // name is another language
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("imdb_id") val imdbId: String?
)
