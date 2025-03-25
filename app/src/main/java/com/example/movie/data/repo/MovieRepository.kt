package com.example.movie.data.repo

import com.example.movie.data.api.MovieApi
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.domain.repo.MovieRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(private val api: MovieApi) : MovieRepo {
    override suspend fun getTrendingMovies(time: String): Response<NetWorkResponse<List<MovieResponse>>> = api.getTrendingMovies(time)
    override suspend fun getMovieDetail(movieId: Int): Response<MovieDetailResponse> = api.getMovieDetail(movieId)
}
