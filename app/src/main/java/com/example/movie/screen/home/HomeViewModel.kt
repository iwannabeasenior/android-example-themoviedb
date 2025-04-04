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
import com.example.movie.screen.home.cache.HomeCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUC: GetTrendingMovieUC,
    private val searchByKeywordUC: SearchByKeywordUC
): ViewModel() {

    val cache: HomeCache = HomeCache.getInstance()
    val trendingUiState: MutableState<TrendingUiState> = mutableStateOf(TrendingUiState.Loading)
    val trendingWeekUiState: MutableState<TrendingWeekUiState> = mutableStateOf(TrendingWeekUiState.Loading)
    val queryResult: MutableStateFlow<List<SearchResult>> = MutableStateFlow(emptyList())

    init {
        getTrendingMovies()
    }
    fun getTrendingMovies() {
        viewModelScope.launch {
            val result = getMovieUC.execute(TrendingMode.day.name)
            trendingUiState.value = when (result) {
                is Result.Loading -> TrendingUiState.Loading
                is Result.Success -> TrendingUiState.Success(result.data)
                is Result.Error -> TrendingUiState.Error
            }
            val result2 = getMovieUC.execute(TrendingMode.week.name)
            trendingWeekUiState.value = when (result2) {
                is Result.Loading -> TrendingWeekUiState.Loading
                is Result.Success -> TrendingWeekUiState.Success(result2.data)
                is Result.Error -> TrendingWeekUiState.Error
            }
        }
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
sealed interface TrendingWeekUiState {
    data class Success(val movies: List<Movie>): TrendingWeekUiState
    data object Error: TrendingWeekUiState
    data object Loading: TrendingWeekUiState
}
