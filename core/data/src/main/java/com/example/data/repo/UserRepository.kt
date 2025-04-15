package com.example.data.repo

import com.example.data.api.UserApi
import com.example.model.request.AddRatingParams
import com.example.model.request.AddToFavoriteParams
import com.example.model.request.AddToWatchListParams
import com.example.model.request.CreateListParams
import com.example.model.request.LogoutParams
import com.example.model.request.MediaIdParams
import com.example.model.request.RequestSessionParams
import com.example.model.request.V4RequestTokenParams
import com.example.domain.repo.UserRepo
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
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
) : UserRepo {
    override suspend fun requestToken(): Response<RequestTokenResponse> = userApi.requestToken()
    override suspend fun requestSession(token: String): Response<RequestSessionResponse> {
        return userApi.requestSession(RequestSessionParams(token).createRawRequestBody())
    }

    override suspend fun getUserDetail(): Response<UserDetailResponse> {
        return userApi.getUserDetail()
    }

    override suspend fun getUserWatchList(): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserWatchList()
    }

    override suspend fun getUserRatedList(
    ): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserRatedList()
    }

    override suspend fun getUserFavoriteList(
    ): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserFavoriteList()
    }

    override suspend fun getUserListsList(
    ): Response<NetWorkResponse<List<ListsResponse>>> {
        return userApi.getUserListsList()
    }

    override suspend fun addToFavorite(
        media_type: String,
        media_id: Int,
        favorite: Boolean
    ): Response<StatusResponse> {
        return userApi.addToFavorite(
            params = AddToFavoriteParams(
                media_type = media_type,
                media_id = media_id,
                favorite = favorite
            ).createRawRequestBody()
        )
    }

    override suspend fun addToWatchList(
        media_type: String,
        media_id: Int,
        watch_list: Boolean
    ): Response<StatusResponse> {
        return userApi.addToWatchList(
            params = AddToWatchListParams(
                media_type = media_type,
                media_id = media_id,
                watchlist = watch_list
            ).createRawRequestBody()
        )
    }

    override suspend fun addRating(
        movieId: Int,
        rating: Int
    ): Response<StatusResponse> {
        return userApi.addRating(
            movieId = movieId,
            params = AddRatingParams(value = rating).createRawRequestBody()
        )
    }

    override suspend fun deleteRating(
        movieId: Int,
    ): Response<StatusResponse> {
        return userApi.deleteRating(
            movieId = movieId,
        )
    }

    override suspend fun createLists(
        name: String,
        description: String,
        language: String
    ): Response<CreateListResponse> {
        return userApi.createLists(
            CreateListParams(
                name = name,
                description = description,
                language = language
            ).createRawRequestBody()
        )
    }

    override suspend fun deleteLists(
        listId: Int
    ): Response<StatusResponse> {
        return userApi.deleteLists(listId = listId)
    }

    override suspend fun clearLists(
        listId: Int,
        confirm: Boolean
    ): Response<StatusResponse> {
        return userApi.clearLists(
            listId = listId,
            confirm = confirm
        )
    }

    override suspend fun addMovieToLists(
        listId: Int,
        mediaId: Int
    ): Response<StatusResponse> {
        return userApi.addMovieToLists(
            listId = listId,
            params = MediaIdParams(mediaId).createRawRequestBody()
        )
    }

    override suspend fun removeMovieFromLists(
        listId: Int,
        mediaId: Int
    ): Response<StatusResponse> {
        return userApi.removeMovieFromLists(
            listId = listId,
            params = MediaIdParams(mediaId).createRawRequestBody()
        )
    }

    override suspend fun checkItemStatus(
        listId: Int,
        movieId: Int
    ): Response<ItemStatusResponse> {
        return userApi.checkItemStatus(
            listId = listId,
            movieId = movieId
        )
    }

    override suspend fun getListsDetail(listId: Int): Response<ListsDetailResponse> {
        return userApi.getListsDetail(listId)
    }

    override suspend fun getMovieAccountState(movieId: Int): Response<MovieAccountStateResponse> {
        return userApi.getMovieAccountState(movieId)
    }

    override suspend fun requestTokenV4(redirectTo: String): Response<V4RequestTokenResponse> {
        return userApi.requestTokenV4(V4RequestTokenParams(redirectTo).createRawRequestBody())
    }

    override suspend fun requestAccessTokenV4(requestToken: String): Response<V4RequestAccessTokenResponse> {
        return userApi.requestAccessTokenV4(RequestSessionParams(requestToken).createRawRequestBody())
    }

    override suspend fun logout(accessToken: String): Response<StatusResponse> {
        return userApi.logout(LogoutParams(accessToken).createRawRequestBody())
    }
}


