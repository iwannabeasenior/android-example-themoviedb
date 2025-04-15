package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.CastMember
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