package com.example.movie.domain.repo

import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepo {
    suspend fun getTrendingMovies(movie: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getMovieDetail(movieId: Int) : Response<MovieDetailResponse>
}