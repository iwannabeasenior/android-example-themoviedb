package com.example.movie.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.data.response.SearchResult
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.Movie
import com.example.movie.domain.usecase.GetTrendingMovieUC
import com.example.movie.domain.usecase.SearchByKeywordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUC: GetTrendingMovieUC,
    private val searchByKeywordUC: SearchByKeywordUC
): ViewModel() {

//    private val _uiState = MutableStateFlow<TrendingUiState>(TrendingUiState.Loading)
    val trendingUiState: MutableState<TrendingUiState> = mutableStateOf(TrendingUiState.Loading)
    val queryResult: MutableStateFlow<List<SearchResult>> = MutableStateFlow(emptyList())

    init {
        getTrendingMovies(TrendingMode.day.name)
        getTrendingMovies(TrendingMode.week.name)
    }
    fun getTrendingMovies(time: String) {
        viewModelScope.launch {
            val result = getMovieUC.execute(time)
            trendingUiState.value = when (result) {
                is Result.Loading -> TrendingUiState.Loading
                is Result.Success -> TrendingUiState.Success(result.data)
                is Result.Error -> TrendingUiState.Error
            }
        }
        println(queryResult)
    }
    fun searchByKeyword(keyword: String) {
        viewModelScope.launch {
            val result = searchByKeywordUC.execute(keyword)
            queryResult.value = when (result) {
                is Result.Loading -> emptyList()
                is Result.Error -> emptyList()
                is Result.Success -> result.data
            }
        }
    }
}

sealed interface TrendingUiState {
    data class Success(val movies: List<Movie>): TrendingUiState
    data object Error: TrendingUiState
    data object Loading: TrendingUiState
}
