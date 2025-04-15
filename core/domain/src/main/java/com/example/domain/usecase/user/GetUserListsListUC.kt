package com.example.domain.usecase.user

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.mapper.MovieMapper
import com.example.model.response.ListsResponse
import javax.inject.Inject

class GetUserListsListUC @Inject constructor(
    private val userRepo: UserRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(): Result<List<ListsResponse>> {
        return handleResponse(userRepo.getUserListsList(), mapper::mapNetWorkResponse)
    }
}