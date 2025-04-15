package com.example.domain.usecase.user

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.Movie
import javax.inject.Inject

class GetUserWatchListUC @Inject constructor(private val userRepo: UserRepo, private val mapper: MovieMapper) {
    suspend fun execute(): Result<List<Movie>> {
        return handleResponse(userRepo.getUserWatchList(), mapper::mapListMovie)
    }
}