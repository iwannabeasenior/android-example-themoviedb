package com.example.movie.screen.home

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.Movie
import com.example.movie.domain.usecase.GetTrendingMovieUC
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUC: GetTrendingMovieUC
): ViewModel() {

//    private val _uiState = MutableStateFlow<TrendingUiState>(TrendingUiState.Loading)
    val trendingUiState: MutableState<TrendingUiState> = mutableStateOf(TrendingUiState.Loading)

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
                is Result.Error -> TrendingUiState.Failer
            }
        }
    }
}

sealed interface TrendingUiState {
    data class Success(val movies: List<Movie>): TrendingUiState
    data object Failer: TrendingUiState
    data object Loading: TrendingUiState
}
