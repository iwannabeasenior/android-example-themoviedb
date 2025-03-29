package com.example.movie.domain.repo

import com.example.movie.data.response.CreditMoviesResponse
import com.example.movie.data.response.MovieCreditsResponse
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.data.response.PersonDetailResponse
import com.example.movie.data.response.SearchResult
import com.example.movie.data.response.VideoResponse
import com.example.movie.domain.model.MovieCredits
import retrofit2.Response

interface MovieRepo {
    suspend fun getTrendingMovies(movie: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getMovieDetail(movieId: Int) : Response<MovieDetailResponse>
    suspend fun getVideosMovie(movieId: Int): Response<NetWorkResponse<List<VideoResponse>>>
    suspend fun getListCast(movieId: Int) : Response<MovieCreditsResponse>
    suspend fun getPersonDetail(personId: Int): Response<PersonDetailResponse>
    suspend fun getPersonMovies(personId: Int): Response<CreditMoviesResponse>
//    suspend fun searchKeyword(keyword: String): Response<List<SearchResult>>
}