package com.example.movie.data.repo

import com.example.movie.data.api.UserApi
import com.example.movie.data.request.AddRatingParams
import com.example.movie.data.request.AddToFavoriteParams
import com.example.movie.data.request.AddToWatchListParams
import com.example.movie.data.request.CreateListParams
import com.example.movie.data.request.LogoutParams
import com.example.movie.data.request.MediaIdParams
import com.example.movie.data.request.RequestSessionParams
import com.example.movie.data.request.V4RequestTokenParams
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
import com.example.movie.domain.repo.UserRepo
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

    override suspend fun getUserWatchList(
        accountId: Int,
        sessionId: String
    ): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserWatchList(
            accountId = accountId,
            sessionId = sessionId
        )
    }

    override suspend fun getUserRatedList(
        accountId: Int,
        sessionId: String
    ): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserRatedList(accountId, sessionId)
    }

    override suspend fun getUserFavoriteList(
        accountId: Int,
        sessionId: String
    ): Response<NetWorkResponse<List<MovieResponse>>> {
        return userApi.getUserFavoriteList(accountId, sessionId)
    }

    override suspend fun getUserListsList(
        accountId: Int,
        sessionId: String
    ): Response<NetWorkResponse<List<ListsResponse>>> {
        return userApi.getUserListsList(accountId, sessionId)
    }

    override suspend fun addToFavorite(
        accountId: Int,
        sessionId: String,
        media_type: String,
        media_id: Int,
        favorite: Boolean
    ): Response<StatusResponse> {
        return userApi.addToFavorite(
            accountId = accountId,
            sessionId = sessionId,
            params = AddToFavoriteParams(
                media_type = media_type,
                media_id = media_id,
                favorite = favorite
            ).createRawRequestBody()
        )
    }

    override suspend fun addToWatchList(
        accountId: Int,
        sessionId: String,
        media_type: String,
        media_id: Int,
        watch_list: Boolean
    ): Response<StatusResponse> {
        return userApi.addToWatchList(
            accountId = accountId,
            sessionId = sessionId,
            params = AddToWatchListParams(
                media_type = media_type,
                media_id = media_id,
                watch_list = watch_list
            ).createRawRequestBody()
        )
    }

    override suspend fun addRating(
        movieId: Int,
        sessionId: String,
        rating: Int
    ): Response<StatusResponse> {
        return userApi.addRating(
            movieId = movieId,
            sessionId = sessionId,
            params = AddRatingParams(value = rating).createRawRequestBody()
        )
    }

    override suspend fun deleteRating(
        movieId: Int,
        sessionId: String
    ): Response<StatusResponse> {
        return userApi.deleteRating(
            movieId = movieId,
            sessionId = sessionId
        )
    }

    override suspend fun createLists(
        sessionId: String,
        name: String,
        description: String,
        language: String
    ): Response<CreateListResponse> {
        return userApi.createLists(sessionId,
            CreateListParams(
                name = name,
                description = description,
                language = language
            ).createRawRequestBody()
        )
    }

    override suspend fun deleteLists(
        sessionId: String,
        listId: Int
    ): Response<StatusResponse> {
        return userApi.deleteLists(sessionId = sessionId, listId = listId)
    }

    override suspend fun clearLists(
        sessionId: String,
        listId: Int,
        confirm: Boolean
    ): Response<StatusResponse> {
        return userApi.clearLists(
            sessionId = sessionId,
            listId = listId,
            confirm = confirm
        )
    }

    override suspend fun addMovieToLists(
        sessionId: String,
        listId: Int,
        mediaId: Int
    ): Response<StatusResponse> {
        return userApi.addMovieToLists(
            sessionId = sessionId,
            listId = listId,
            params = MediaIdParams(mediaId).createRawRequestBody()
        )
    }

    override suspend fun removeMovieFromLists(
        sessionId: String,
        listId: Int,
        mediaId: Int
    ): Response<StatusResponse> {
        return userApi.removeMovieFromLists(
            sessionId = sessionId,
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


