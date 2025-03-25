package com.example.movie.domain.usecase

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.data.response.MovieResponse
import com.example.movie.data.response.NetWorkResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.Movie
import com.example.movie.domain.repo.MovieRepo
import javax.inject.Inject

class GetTrendingMovieUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(movie: String): Result<List<Movie>> {
        return handleResponse<NetWorkResponse<List<MovieResponse>>, List<Movie>>(repo.getTrendingMovies(movie), map = mapper::mapListMovie)
    }
}