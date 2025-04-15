package com.example.data.api
import com.example.model.response.CreditMoviesResponse
import com.example.model.response.GenreResponse
import com.example.model.response.GenresResponse
import com.example.model.response.MovieCreditsResponse
import com.example.model.response.MovieDetailResponse
import com.example.model.response.MovieResponse
import com.example.model.response.NetWorkResponse
import com.example.model.response.PersonDetailResponse
import com.example.model.response.SearchResult
import com.example.model.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieApi {
    @GET("3/trending/movie/{time}?language=en-US")
    suspend fun getTrendingMovies(@Path("time") time: String) : Response<NetWorkResponse<List<MovieResponse>>>
    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetailResponse>
    @GET("3/movie/{movie_id}/videos")
    suspend fun getVideosMovie(@Path("movie_id") movieId: Int): Response<NetWorkResponse<List<VideoResponse>>>
    @GET("3/movie/{movie_id}/credits")
    suspend fun getCastsMovie(@Path("movie_id") movieId: Int): Response<MovieCreditsResponse>
    @GET("3/person/{person_id}")
    suspend fun getPeopleDetail(@Path("person_id") personId: Int): Response<PersonDetailResponse>
    @GET("3/person/{person_id}/movie_credits")
    suspend fun getCreditMovies(@Path("person_id") personId: Int): Response<CreditMoviesResponse>
    @GET("3/search/multi")
    suspend fun searchByKeyWord(@Query("query") keyword: String): Response<List<SearchResult>> // media_type to indicate specific type

    @GET("/3/genre/movie/list")
    suspend fun getMovieGenres(): Response<GenresResponse>
    suspend fun discoverMovies(@QueryMap filters: Map<String, Any?>): Response<NetWorkResponse<List<MovieResponse>>>
}