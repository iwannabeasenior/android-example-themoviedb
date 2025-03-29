package com.example.movie.data.repo

import com.example.movie.data.api.MovieApi
import com.example.movie.data.response.SearchResult
import com.example.movie.di.NormalAPI
import com.example.movie.di.SearchAPI
import com.example.movie.domain.repo.SearchRepo
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(@SearchAPI private val api: MovieApi): SearchRepo {
    override suspend fun searchKeyword(keyword: String): Response<List<SearchResult>> = api.searchByKeyWord(keyword)
}