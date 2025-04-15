package com.example.data.api

import com.example.model.response.CreateListResponse
import com.example.model.response.ItemStatusResponse
import com.example.model.response.ListsDetailResponse
import com.example.model.response.ListsResponse
import com.example.model.response.MovieAccountStateResponse
import com.example.model.response.MovieResponse
import com.example.model.response.NetWorkResponse
import com.example.model.response.RequestSessionResponse
import com.example.model.response.RequestTokenResponse
import com.example.model.response.StatusResponse
import com.example.model.response.UserDetailResponse
import com.example.model.response.V4RequestAccessTokenResponse
import com.example.model.response.V4RequestTokenResponse
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
    @POST("3/account/123/watchlist")
    suspend fun addToWatchList(@Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/123/watchlist/movies")
    suspend fun getUserWatchList(): Response<NetWorkResponse<List<MovieResponse>>>
    // End Watch List

    // Favorite
    @POST("3/account/123/favorite")
    suspend fun addToFavorite(@Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/123/favorite/movies")
    suspend fun getUserFavoriteList(): Response<NetWorkResponse<List<MovieResponse>>>
    // End Favorite

    // Rating
    @POST("3/movie/{movie_id}/rating")
    suspend fun addRating(@Path("movie_id") movieId: Int, @Body params: RequestBody): Response<StatusResponse>

    @GET("3/account/123/rated/movies")
    suspend fun getUserRatedList(): Response<NetWorkResponse<List<MovieResponse>>>

    @DELETE("3/movie/{movie_id}/rating")
    suspend fun deleteRating(@Path("movie_id") movieId: Int): Response<StatusResponse>
    // End Rating

    //  Lists
    @GET("3/account/123/lists")
    suspend fun getUserListsList(): Response<NetWorkResponse<List<ListsResponse>>>

    @POST("3/list")
    suspend fun createLists(@Body params: RequestBody): Response<CreateListResponse>

    @DELETE("3/list/{list_id}")
    suspend fun deleteLists(@Path("list_id") listId: Int): Response<StatusResponse>

    @POST("3/list/{list_id}/clear")
    suspend fun clearLists(@Path("list_id") listId: Int, @Query("confirm") confirm: Boolean): Response<StatusResponse>

    @POST("3/list/{list_id}/add_item")
    suspend fun addMovieToLists(@Path("list_id") listId: Int, @Body params: RequestBody): Response<StatusResponse>

    @POST("3/list/{list_id}/remove_item")
    suspend fun removeMovieFromLists(@Path("list_id") listId: Int, @Body params: RequestBody): Response<StatusResponse>

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