package com.example.movie.data.repo

import com.example.movie.data.api.MovieApi
import com.example.movie.data.response.CreditMoviesResponse
import com.example.movie.data.response.MovieCreditsResponse
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.data.response.PersonDetailResponse
import com.example.movie.data.response.SearchResult
import com.example.movie.data.response.VideoResponse
import com.example.movie.di.NormalAPI
import com.example.movie.domain.repo.MovieRepo
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(@NormalAPI private val api: MovieApi) : MovieRepo {
    override suspend fun getTrendingMovies(time: String): Response<NetWorkResponse<List<MovieResponse>>> = api.getTrendingMovies(time)
    override suspend fun getMovieDetail(movieId: Int): Response<MovieDetailResponse> = api.getMovieDetail(movieId)
    override suspend fun getVideosMovie(movieId: Int): Response<NetWorkResponse<List<VideoResponse>>> = api.getVideosMovie(movieId)
    override suspend fun getListCast(movieId: Int): Response<MovieCreditsResponse> = api.getCastsMovie(movieId)
    override suspend fun getPersonDetail(personId: Int): Response<PersonDetailResponse> = api.getPeopleDetail(personId)
    override suspend fun getPersonMovies(personId: Int): Response<CreditMoviesResponse> = api.getCreditMovies(personId)
//    override suspend fun searchKeyword(keyword: String): Response<List<SearchResult>> = api.searchByKeyWord(keyword)
}
