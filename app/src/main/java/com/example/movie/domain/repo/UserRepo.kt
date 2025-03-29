package com.example.movie.domain.repo

import android.adservices.ondevicepersonalization.RequestToken
import com.example.movie.data.response.RequestSessionResponse
import com.example.movie.data.response.RequestTokenResponse
import retrofit2.Response

interface UserRepo {
    suspend fun requestToken(): Response<RequestTokenResponse>
    suspend fun requestSession(token: String): Response<RequestSessionResponse>
}