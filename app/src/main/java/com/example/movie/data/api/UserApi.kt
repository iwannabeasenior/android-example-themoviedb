package com.example.movie.data.api

import com.example.movie.data.request.RequestSessionParams
import com.example.movie.data.response.RequestSessionResponse
import com.example.movie.data.response.RequestTokenResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("authentication/token/new")
    suspend fun requestToken(): Response<RequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun requestSession(
        @Body params: RequestBody
    ): Response<RequestSessionResponse>
}