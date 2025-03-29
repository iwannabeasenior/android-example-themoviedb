package com.example.movie.domain.usecase

import com.example.movie.data.mapper.MovieMapper
import com.example.movie.data.response.PersonDetailResponse
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.model.PersonDetail
import com.example.movie.domain.repo.MovieRepo
import javax.inject.Inject

class GetPersonDetailUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(personId: Int): Result<PersonDetail> {
        return handleResponse<PersonDetailResponse, PersonDetail>(repo.getPersonDetail(personId), map = mapper::mapPersonDetail)
    }
}