package com.example.movie.domain.usecase.user

import com.example.movie.data.mapper.UserMapper
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.MovieAccountState
import com.example.movie.domain.repo.UserRepo
import javax.inject.Inject

class GetMovieAccountStateUC @Inject constructor(
    private val mapper: UserMapper,
    private val repo: UserRepo
) {
    suspend fun execute(movieId: Int): Result<MovieAccountState> {
        return handleResponse(response = repo.getMovieAccountState(movieId), map = mapper::mapMovieAccountState)
    }
}