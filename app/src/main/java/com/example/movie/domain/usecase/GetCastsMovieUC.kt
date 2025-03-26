package com.example.movie.domain.usecase

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.CastMember
import com.example.movie.domain.repo.MovieRepo
import javax.inject.Inject

class GetCastsMovieUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(movieId: Int) : Result<List<CastMember>> {
        return handleResponse(
            response = repo.getListCast(movieId),
            map = mapper::mapListCast
        )
    }
}