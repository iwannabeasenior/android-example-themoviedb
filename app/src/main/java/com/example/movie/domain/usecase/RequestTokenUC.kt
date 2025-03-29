package com.example.movie.domain.usecase

import com.example.movie.data.response.RequestTokenResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.repo.UserRepo
import javax.inject.Inject

class RequestTokenUC @Inject constructor(
    private val userRepo: UserRepo
) {
    suspend fun execute(): Result<RequestTokenResponse> {
        return handleResponse(userRepo.requestToken())
    }
}