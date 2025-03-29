package com.example.movie.domain.usecase

import com.example.movie.data.response.SearchResult
import com.example.movie.domain.base.Result
import com.example.movie.domain.base.handleResponse
import com.example.movie.domain.repo.MovieRepo
import com.example.movie.domain.repo.SearchRepo
import javax.inject.Inject

class SearchByKeywordUC @Inject constructor(private val repo: SearchRepo) {
    suspend fun execute(keyword: String) : Result<List<SearchResult>>{
        return handleResponse(repo.searchKeyword(keyword))
    }
}