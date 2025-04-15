package com.example.domain.repo

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
import retrofit2.Response

interface UserRepo {
    suspend fun requestToken(): Response<RequestTokenResponse>
    suspend fun requestSession(token: String): Response<RequestSessionResponse>
    suspend fun getUserDetail(): Response<UserDetailResponse>
    suspend fun getUserWatchList(): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserRatedList(): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserFavoriteList(): Response<NetWorkResponse<List<MovieResponse>>>
    suspend fun getUserListsList(): Response<NetWorkResponse<List<ListsResponse>>>
    suspend fun addToFavorite(media_type: String, media_id: Int, favorite: Boolean): Response<StatusResponse>
    suspend fun addToWatchList(media_type: String, media_id: Int, watch_list: Boolean): Response<StatusResponse>
    suspend fun addRating(movieId: Int, rating: Int): Response<StatusResponse>
    suspend fun deleteRating(movieId: Int): Response<StatusResponse>
    suspend fun createLists(name: String, description: String, language: String): Response<CreateListResponse>
    suspend fun deleteLists(listId: Int): Response<StatusResponse>
    suspend fun clearLists(listId: Int, confirm: Boolean): Response<StatusResponse>
    suspend fun addMovieToLists(listId: Int, mediaId: Int): Response<StatusResponse>
    suspend fun removeMovieFromLists(listId: Int, mediaId: Int): Response<StatusResponse>
    suspend fun checkItemStatus(listId: Int, movieId: Int): Response<ItemStatusResponse>
    suspend fun getListsDetail(listId: Int): Response<ListsDetailResponse>
    suspend fun getMovieAccountState(movieId: Int): Response<MovieAccountStateResponse>

    // v4
    suspend fun requestTokenV4(redirectTo: String): Response<V4RequestTokenResponse>
    suspend fun requestAccessTokenV4(requestToken: String): Response<V4RequestAccessTokenResponse>
    suspend fun logout(accessToken: String): Response<StatusResponse>
}