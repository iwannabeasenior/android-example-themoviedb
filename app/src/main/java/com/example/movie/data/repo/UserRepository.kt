package com.example.movie.data.repo

import com.example.movie.data.api.UserApi
import com.example.movie.data.response.RequestSessionResponse
import com.example.movie.data.response.RequestTokenResponse
import com.example.movie.domain.repo.UserRepo
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
) : UserRepo {
    override suspend fun requestToken(): Response<RequestTokenResponse> = userApi.requestToken()
    override suspend fun requestSession(token: String): Response<RequestSessionResponse> {
        val rawJson = """{"request_token":"$token"}"""
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),
            rawJson
        )
        return userApi.requestSession(requestBody)
    }
}