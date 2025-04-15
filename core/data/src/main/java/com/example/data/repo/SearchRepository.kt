package com.example.data.repo

import com.example.data.api.MovieApi
import com.example.domain.repo.SearchRepo
import com.example.model.response.SearchResult
import retrofit2.Response
import javax.inject.Inject

//class SearchRepository @Inject constructor(@SearchAPI private val api: MovieApi): SearchRepo {
//    override suspend fun searchKeyword(keyword: String): Response<List<SearchResult>> = api.searchByKeyWord(keyword)
//}