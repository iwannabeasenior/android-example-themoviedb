package com.example.movie.domain.usecase.user

import com.example.movie.data.response.RequestSessionResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.repo.UserRepo
import javax.inject.Inject

class RequestSessionUC @Inject constructor(
    private val repo: UserRepo
) {
    suspend fun execute(token: String): Result<RequestSessionResponse> {
        return handleResponse(repo.requestSession(token))
    }
}