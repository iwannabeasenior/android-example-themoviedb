package com.example.domain.usecase.user

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.UserRepo
import com.example.model.mapper.UserMapper
import com.example.model.model.MovieAccountState
import javax.inject.Inject

class GetMovieAccountStateUC @Inject constructor(
    private val mapper: UserMapper,
    private val repo: UserRepo
) {
    suspend fun execute(movieId: Int): Result<MovieAccountState> {
        return handleResponse(response = repo.getMovieAccountState(movieId), map = mapper::mapMovieAccountState)
    }
}