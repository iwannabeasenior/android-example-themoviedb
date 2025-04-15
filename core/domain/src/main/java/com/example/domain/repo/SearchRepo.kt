package com.example.domain.repo

import com.example.model.response.SearchResult
import retrofit2.Response

interface SearchRepo {
    suspend fun searchKeyword(keyword: String): Response<List<SearchResult>>
}