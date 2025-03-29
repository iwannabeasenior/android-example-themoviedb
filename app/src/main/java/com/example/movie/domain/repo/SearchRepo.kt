package com.example.movie.domain.repo

import com.example.movie.data.response.SearchResult
import retrofit2.Response

interface SearchRepo {
    suspend fun searchKeyword(keyword: String): Response<List<SearchResult>>
}