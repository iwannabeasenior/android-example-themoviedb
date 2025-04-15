package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.MovieRepo
import com.example.model.mapper.MovieMapper
import com.example.model.model.PersonDetail
import com.example.model.response.PersonDetailResponse
import javax.inject.Inject

class GetPersonDetailUC @Inject constructor(
    private val repo: MovieRepo,
    private val mapper: MovieMapper
) {
    suspend fun execute(personId: Int): Result<PersonDetail> {
        return handleResponse<PersonDetailResponse, PersonDetail>(repo.getPersonDetail(personId), map = mapper::mapPersonDetail)
    }
}