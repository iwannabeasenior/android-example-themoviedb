package com.example.movie.domain.repo

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
import retrofit2.Response

interface UserRepo {
    suspend fun requestToken(): Response<RequestTokenResponse>
    suspend fun requestSession(token: String): Response<RequestSessionResponse>
    suspend fun getUserDetail(): Response<UserDetailResponse>
    suspend fun getUserWatchList(accountId: Int, sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserRatedList(accountId: Int, sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserFavoriteList(accountId: Int, sessionId: String): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserListsList(accountId: Int, sessionId: String): Response<NetWorkResponse<List<ListsResponse>>>
    suspend fun addToFavorite(accountId: Int, sessionId: String, media_type: String, media_id: Int, favorite: Boolean): Response<StatusResponse>
    suspend fun addToWatchList(accountId: Int, sessionId: String, media_type: String, media_id: Int, watch_list: Boolean): Response<StatusResponse>
    suspend fun addRating(movieId: Int, sessionId: String, rating: Int): Response<StatusResponse>
    suspend fun deleteRating(movieId: Int, sessionId: String): Response<StatusResponse>
    suspend fun createLists(sessionId: String, name: String, description: String, language: String): Response<CreateListResponse>
    suspend fun deleteLists(sessionId: String, listId: Int): Response<StatusResponse>
    suspend fun clearLists(sessionId: String, listId: Int, confirm: Boolean): Response<StatusResponse>
    suspend fun addMovieToLists(sessionId: String, listId: Int, mediaId: Int): Response<StatusResponse>
    suspend fun removeMovieFromLists(sessionId: String, listId: Int, mediaId: Int): Response<StatusResponse>
    suspend fun checkItemStatus(listId: Int, movieId: Int): Response<ItemStatusResponse>
    suspend fun getListsDetail(listId: Int): Response<ListsDetailResponse>
    suspend fun getMovieAccountState(movieId: Int): Response<MovieAccountStateResponse>

    // v4
    suspend fun requestTokenV4(redirectTo: String): Response<V4RequestTokenResponse>
    suspend fun requestAccessTokenV4(requestToken: String): Response<V4RequestAccessTokenResponse>
    suspend fun logout(accessToken: String): Response<StatusResponse>
}