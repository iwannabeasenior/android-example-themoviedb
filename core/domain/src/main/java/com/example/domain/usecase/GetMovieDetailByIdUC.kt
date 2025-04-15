package com.example.domain.usecase

import com.example.common.base.Result
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.MovieDetail
import com.example.model.response.MovieDetailResponse
import com.example.common.base.handleResponse
import javax.inject.Inject

class GetMovieDetailByIdUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(movieId: Int): Result<MovieDetail> {
        return handleResponse<MovieDetailResponse, MovieDetail>(repo.getMovieDetail(movieId), map = mapper::mapMovieDetail)
    }
}
