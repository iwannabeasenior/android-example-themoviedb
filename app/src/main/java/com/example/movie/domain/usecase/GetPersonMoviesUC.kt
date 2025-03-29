package com.example.movie.domain.usecase

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.data.response.CreditMoviesResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.Movie
import com.example.movie.domain.model.Video
import com.example.movie.domain.repo.MovieRepo
import javax.inject.Inject

class GetPersonMoviesUC @Inject constructor(private val repo: MovieRepo, private val mapper: MovieMapper) {
    suspend fun execute(personId: Int): Result<CreditMoviesResponse> {
        return handleResponse(response = repo.getPersonMovies(personId))
    }
}