package com.example.domain.usecase.user
import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.mapper.UserMapper
import com.example.model.model.UserDetail
import javax.inject.Inject

class GetUserDetailUC @Inject constructor(
    private val userRepo: UserRepo,
    private val mapper: UserMapper
) {
    suspend fun execute(accountId: String): Result<UserDetail> {
        return handleResponse(userRepo.getUserDetail(), map = mapper::mapUserDetail)
    }
}