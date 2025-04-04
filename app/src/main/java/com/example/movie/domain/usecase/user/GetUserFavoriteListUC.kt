package com.example.movie.domain.usecase.user

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.Movie
import com.example.movie.domain.repo.UserRepo
import javax.inject.Inject


class GetUserFavoriteListUC @Inject constructor(private val userRepo: UserRepo, private val mapper: MovieMapper) {
    suspend fun execute(accountId: Int, sessionId: String): Result<List<Movie>> {
        return handleResponse(userRepo.getUserFavoriteList(accountId, sessionId), mapper::mapListMovie)
    }
}