package com.example.domain.usecase.user

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.response.RequestSessionResponse
import javax.inject.Inject

class RequestSessionUC @Inject constructor(
    private val repo: UserRepo
) {
    suspend fun execute(token: String): Result<RequestSessionResponse> {
        return handleResponse(repo.requestSession(token))
    }
}