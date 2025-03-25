package com.example.movie.domain.usecase

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.data.response.MovieDetailResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.repo.MovieRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailByIdUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(movieId: Int): Result<MovieDetail> {
        return handleResponse<MovieDetailResponse, MovieDetail>(repo.getMovieDetail(movieId), map = mapper::mapMovieDetail)
    }
}