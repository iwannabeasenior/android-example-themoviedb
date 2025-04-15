package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.response.CreditMoviesResponse
import javax.inject.Inject

class GetPersonMoviesUC @Inject constructor(private val repo: MovieRepo, private val mapper: MovieMapper) {
    suspend fun execute(personId: Int): Result<CreditMoviesResponse> {
        return handleResponse(response = repo.getPersonMovies(personId))
    }
}