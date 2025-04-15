package com.example.domain.repo
import com.example.model.request.DiscoverMoviesParams
import com.example.model.response.CreditMoviesResponse
import com.example.model.response.GenreResponse
import com.example.model.response.GenresResponse
import com.example.model.response.MovieCreditsResponse
import com.example.model.response.MovieDetailResponse
import com.example.model.response.MovieResponse
import com.example.model.response.NetWorkResponse
import com.example.model.response.PersonDetailResponse
import com.example.model.response.VideoResponse
import retrofit2.Response



interface MovieRepo {
    suspend fun getTrendingMovies(movie: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getMovieDetail(movieId: Int) : Response<MovieDetailResponse>
    suspend fun getVideosMovie(movieId: Int): Response<NetWorkResponse<List<VideoResponse>>>
    suspend fun getListCast(movieId: Int) : Response<MovieCreditsResponse>
    suspend fun getPersonDetail(personId: Int): Response<PersonDetailResponse>
    suspend fun getPersonMovies(personId: Int): Response<CreditMoviesResponse>
//    suspend fun searchKeyword(keyword: String): Response<List<SearchResult>>
    suspend fun getMovieGenres(): Response<GenresResponse>
    suspend fun discoverMovies(params: DiscoverMoviesParams): Response<NetWorkResponse<List<MovieResponse>>>
}