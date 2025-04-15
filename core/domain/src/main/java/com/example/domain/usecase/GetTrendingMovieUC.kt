package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.Movie
import com.example.model.response.MovieResponse
import com.example.model.response.NetWorkResponse
import javax.inject.Inject

class GetTrendingMovieUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(movie: String): Result<List<Movie>> {
        return handleResponse<NetWorkResponse<List<MovieResponse>>, List<Movie>>(repo.getTrendingMovies(movie), map = mapper::mapListMovie)
    }
}