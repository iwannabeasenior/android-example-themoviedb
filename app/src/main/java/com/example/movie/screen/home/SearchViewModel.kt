package com.example.movie.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain.repo.SearchRepo
import com.example.model.response.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

//@HiltViewModel
//class SearchViewModel @Inject constructor(
//    private val searchRepo: SearchRepo,
//    private val savedStateHandle: SavedStateHandle
//) : ViewModel() {
//    val searchQuery = savedStateHandle.getStateFlow<String>(SEARCH_QUERY, "")
//
//    fun updateSearchQuery(query: String) {
//        savedStateHandle[SEARCH_QUERY] = query
//    }
//
//    companion object {
//        private const val SEARCH_QUERY ="searchQuery"
//    }
//}



interface SearchResultState {
    data class Success(val data: List<SearchResult>): SearchResultState
    data object Loading: SearchResultState
    data object Error: SearchResultState
}