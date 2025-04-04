package com.example.movie.data.api

import com.example.movie.data.request.Params
import com.example.movie.data.response.CreateListResponse
import com.example.movie.data.response.ItemStatusResponse
import com.example.movie.data.response.ListsDetailResponse
import com.example.movie.data.response.ListsResponse
import com.example.movie.data.response.MovieAccountStateResponse
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.data.response.RequestSessionResponse
import com.example.movie.data.response.RequestTokenResponse
import com.example.movie.data.response.StatusResponse
import com.example.movie.data.response.UserDetailResponse
import com.example.movie.data.response.V4RequestAccessTokenResponse
import com.example.movie.data.response.V4RequestTokenResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("3/authentication/token/new")
    suspend fun requestToken(): Response<RequestTokenResponse>

    @POST("3/authentication/session/new")
    suspend fun requestSession(
        @Body params: RequestBody
    ): Response<RequestSessionResponse>

    @GET("3/account/123")
    suspend fun getUserDetail(): Response<UserDetailResponse>

    // Watch List
    @POST("3/account/{account_id}/watchlist")
    suspend fun addToWatchList(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String, @Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/{account_id}/watchlist/movies")
    suspend fun getUserWatchList(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>
    // End Watch List

    // Favorite
    @POST("3/account/{account_id}/favorite")
    suspend fun addToFavorite(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String, @Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/{account_id}/favorite/movies")
    suspend fun getUserFavoriteList(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>
    // End Favorite

    // Rating
    @POST("3/movie/{movie_id}/rating")
    suspend fun addRating(@Path("movie_id") movieId: Int, @Query("session_id") sessionId: String, @Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/{account_id}/rated/movies")
    suspend fun getUserRatedList(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>

    @DELETE("3/movie/{movie_id}/rating")
    suspend fun deleteRating(@Path("movie_id") movieId: Int, @Query("session_id") sessionId: String): Response<StatusResponse>
    // End Rating

    //  Lists
    @GET("3/account/{account_id}/lists")
    suspend fun getUserListsList(@Path("account_id") accountId: Int, @Query("session_id") sessionId: String): Response<NetWorkResponse<List<ListsResponse>>>

    @POST("3/list")
    suspend fun createLists(@Query("session_id") sessionId: String, @Body params: RequestBody): Response<CreateListResponse>

    @DELETE("3/list/{list_id}")
    suspend fun deleteLists(@Query("session_id") sessionId: String, @Path("list_id") listId: Int): Response<StatusResponse>

    @POST("3/list/{list_id}/clear")
    suspend fun clearLists(@Query("session_id") sessionId: String, @Path("list_id") listId: Int, @Query("confirm") confirm: Boolean): Response<StatusResponse>

    @POST("3/list/{list_id}/add_item")
    suspend fun addMovieToLists(@Query("session_id") sessionId: String, @Path("list_id") listId: Int, @Body params: RequestBody): Response<StatusResponse>

    @POST("3/list/{list_id}/remove_item")
    suspend fun removeMovieFromLists(@Query("session_id") sessionId: String, @Path("list_id") listId: Int, @Body params: RequestBody): Response<StatusResponse>

    @GET("3/list/{list_id}/item_status")
    suspend fun checkItemStatus(@Path("list_id") listId: Int, @Query("movie_id") movieId: Int): Response<ItemStatusResponse>

    @GET("3/list/{list_id}")
    suspend fun getListsDetail(@Path("list_id") listId: Int): Response<ListsDetailResponse>
    // End Lists


    // Account State for each movie
    @GET("3/movie/{movie_id}/account_states")
    suspend fun getMovieAccountState(@Path("movie_id") movieId: Int): Response<MovieAccountStateResponse>


    /// v4
    @POST("4/auth/request_token")
    suspend fun requestTokenV4(@Body params: RequestBody): Response<V4RequestTokenResponse>

    @POST("4/auth/access_token")
    suspend fun requestAccessTokenV4(@Body params: RequestBody): Response<V4RequestAccessTokenResponse>

    @HTTP(method = "DELETE", path = "4/auth/access_token", hasBody = true)
    suspend fun logout(@Body params: RequestBody): Response<StatusResponse>
}