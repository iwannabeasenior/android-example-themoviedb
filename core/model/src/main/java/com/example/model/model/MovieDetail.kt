package com.example.model.model


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

    fun formatTime() = runtime?.let { time ->
        val hour = (time / 60).run {
            if (this < 10) "0$this"
            else this.toString()
        }
        val minute = (time % 60).run {
            if (this < 10) "0$this"
            else this.toString()
        }
        return "${hour}h ${minute}m"
    } ?: ""

    fun formatGenres() = genres?.joinToString(", ") { it.name.toString() } ?: ""
}


data class Genre(
    val id: Int?,
    val name: String?
)

data class ProductionCompany(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?
)

data class ProductionCountryISO(
    val iso31661: String?,
    val name: String?
)

data class SpokenLanguage(
    val englishName: String?,
    val iso6391: String?,
    val name: String?
)
