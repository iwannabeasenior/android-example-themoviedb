package com.example.data.repo

import com.example.data.api.MovieApi
import com.example.model.request.DiscoverMoviesParams
import com.example.domain.repo.MovieRepo
import com.example.model.response.CreditMoviesResponse
import com.example.model.response.GenresResponse
import com.example.model.response.MovieCreditsResponse
import com.example.model.response.MovieDetailResponse
import com.example.model.response.MovieResponse
import com.example.model.response.NetWorkResponse
import com.example.model.response.PersonDetailResponse
import com.example.model.response.VideoResponse
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(private val api: MovieApi) : MovieRepo {
    override suspend fun getTrendingMovies(time: String): Response<NetWorkResponse<List<MovieResponse>>> = api.getTrendingMovies(time)
    override suspend fun getMovieDetail(movieId: Int): Response<MovieDetailResponse> = api.getMovieDetail(movieId)
    override suspend fun getVideosMovie(movieId: Int): Response<NetWorkResponse<List<VideoResponse>>> = api.getVideosMovie(movieId)
    override suspend fun getListCast(movieId: Int): Response<MovieCreditsResponse> = api.getCastsMovie(movieId)
    override suspend fun getPersonDetail(personId: Int): Response<PersonDetailResponse> = api.getPeopleDetail(personId)
    override suspend fun getPersonMovies(personId: Int): Response<CreditMoviesResponse> = api.getCreditMovies(personId)
    override suspend fun getMovieGenres(): Response<GenresResponse> = api.getMovieGenres()
    override suspend fun discoverMovies(params: DiscoverMoviesParams): Response<NetWorkResponse<List<MovieResponse>>> {
        return api.discoverMovies(filters = params.toMap())
    }
}
