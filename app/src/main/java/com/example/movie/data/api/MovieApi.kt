package com.example.movie.data.api

import com.example.movie.data.response.MovieCreditsResponse
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.data.response.VideoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("trending/movie/{time}?language=en-US")
    suspend fun getTrendingMovies(@Path("time") time: String) : Response<NetWorkResponse<List<MovieResponse>>>
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetailResponse>
    @GET("movie/{movie_id}/videos")
    suspend fun getVideosMovie(@Path("movie_id") movieId: Int): Response<NetWorkResponse<List<VideoResponse>>>
    @GET("movie/{movie_id}/credits")
    suspend fun getCastsMovie(@Path("movie_id") movieId: Int): Response<MovieCreditsResponse>
}