package com.example.movie.domain.usecase.user

import com.example.movie.data.mapper.UserMapper
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.UserDetail
import com.example.movie.domain.repo.UserRepo
import javax.inject.Inject

class GetUserDetailUC @Inject constructor(
    private val userRepo: UserRepo,
    private val mapper: UserMapper
) {
    suspend fun execute(accountId: String): Result<UserDetail> {
        return handleResponse(userRepo.getUserDetail(), map = mapper::mapUserDetail)
    }
}