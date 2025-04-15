package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.Genre
import com.example.model.response.GenreResponse
import com.example.model.response.GenresResponse
import javax.inject.Inject


class GetMovieGenresUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(): Result<List<Genre>> {
        return handleResponse<GenresResponse, List<Genre>>(repo.getMovieGenres(), map = mapper::mapGenres)
    }
}