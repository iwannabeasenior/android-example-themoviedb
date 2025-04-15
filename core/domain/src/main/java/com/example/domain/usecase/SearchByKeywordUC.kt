package com.example.domain.usecase

import com.example.common.base.Result
import com.example.common.base.handleResponse
import com.example.domain.repo.SearchRepo
import com.example.model.response.SearchResult
import javax.inject.Inject

//class SearchByKeywordUC @Inject constructor(private val repo: SearchRepo) {
//    suspend fun execute(keyword: String) : Result<List<SearchResult>>{
//        return handleResponse(repo.searchKeyword(keyword))
//    }
//}