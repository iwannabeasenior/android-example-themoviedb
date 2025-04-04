package com.example.movie.data.mapper

import com.example.movie.data.response.CastMemberResponse
import com.example.movie.data.response.GenreResponse
import com.example.movie.data.response.MovieCreditsResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.PersonDetailResponse
import com.example.movie.data.response.ProductionCompanyResponse
import com.example.movie.data.response.ProductionCountryISOResponse
import com.example.movie.data.response.SpokenLanguageResponse
import com.example.movie.data.response.VideoResponse
import com.example.movie.domain.model.CastMember
import com.example.movie.domain.model.Genre
import com.example.movie.domain.model.Movie
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.model.PersonDetail
import com.example.movie.domain.model.ProductionCompany
import com.example.movie.domain.model.ProductionCountryISO
import com.example.movie.domain.model.SpokenLanguage
import com.example.movie.domain.model.Video
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun <T> mapNetWorkResponse(response: NetWorkResponse<T>): T {
        return response.results
    }


    fun mapListMovie(movies: NetWorkResponse<List<MovieResponse>>): List<Movie> {
        return mapNetWorkResponse(movies).map {
            mapMovie(it)
        }
    }

    fun mapMovie(
        movie: MovieResponse
    ) : Movie {
        return Movie(
            id = movie.id,
            title = movie.title,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            mediaType = movie.mediaType,
            adult = movie.adult,
            originalLanguage = movie.originalLanguage,
            genreIds = movie.genreIds,
            popularity = movie.popularity,
            releaseDate = movie.releaseDate,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            rating = movie.rating
        )
    }
    fun mapMovieDetail(movie: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            adult = movie.adult == true,
            backdropPath = movie.backdropPath,
            belongsToCollection = movie.belongsToCollection,
            budget = movie.budget ?: -1,
            genres = movie.genres?.map { mapGenRe(it) },
            homepage = movie.homepage,
            id = movie.id,
            imdbId = movie.imdbId,
            originCountry = movie.originCountry,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            productionCompanies = movie.productionCompanies?.map { mapProductionCompany(it) },
            productionCountriesISO = movie.productionCountriesISO?.map { mapProductionCompanyISO(it) },
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            spokenLanguages = movie.spokenLanguages?.map { mapSpokenLanguage(it) },
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount
        )
    }
    fun mapGenRe(genre: GenreResponse): Genre {
        return Genre(genre.id, genre.name)
    }
    fun mapSpokenLanguage(item: SpokenLanguageResponse) : SpokenLanguage {
        return SpokenLanguage(
            englishName = item.englishName,
            iso6391 = item.iso6391,
            name = item.name
        )
    }
    fun mapProductionCompanyISO(item: ProductionCountryISOResponse): ProductionCountryISO {
        return ProductionCountryISO(
            item.name,
            item.iso31661
        )
    }
    fun mapProductionCompany(item: ProductionCompanyResponse): ProductionCompany {
        return ProductionCompany(
            id = item.id,
            logoPath = item.logoPath,
            name = item.name,
            originCountry = item.originCountry
        )
    }

    fun mapVideo(item: VideoResponse): Video {
        return Video(
            ios = item.ios,
            name = item.name,
            key = item.key,
            site = item.site,
            size = item.size,
            type = item.type,
            official = item.official,
            publishedAt = item.publishedAt,
            id = item.id
        )
    }
    fun mapListVideo(videos: NetWorkResponse<List<VideoResponse>>): List<Video> {
        return videos.results.map { mapVideo(it) }
    }

    // for credit
    fun mapListCast(credit: MovieCreditsResponse): List<CastMember> {
        return credit.cast.map { mapCast(it) }
    }
    fun mapCast(cast: CastMemberResponse) : CastMember {
        return CastMember(
            adult = cast.adult,
            gender = cast.gender,
            id = cast.id,
            knownForDepartment = cast.knownForDepartment,
            name = cast.name,
            originalName = cast.originalName,
            popularity = cast.popularity,
            profilePath = cast.profilePath,
            castId = cast.castId,
            character = cast.character,
            creditId = cast.creditId,
            order = cast.order
        )
    }
    fun mapPersonDetail(person: PersonDetailResponse): PersonDetail {
        return PersonDetail(
            gender = person.gender,
            id = person.id,
            name = person.name,
            birthday = person.birthday,
            placeOfBirth = person.placeOfBirth,
            biography = person.biography,
            knownFor = person.knownFor,
            popularity = person.popularity,
            alsoKnownAs = person.alsoKnownAs,
            profilePath = person.profilePath,
            imdbId = person.imdbId
        )
    }
}