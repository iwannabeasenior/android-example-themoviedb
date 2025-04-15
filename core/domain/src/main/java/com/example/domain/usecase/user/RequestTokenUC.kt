package com.example.domain.usecase.user

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.response.RequestTokenResponse
import javax.inject.Inject

class RequestTokenUC @Inject constructor(
    private val userRepo: UserRepo
) {
    suspend fun execute(): Result<RequestTokenResponse> {
        return handleResponse(userRepo.requestToken())
    }
}