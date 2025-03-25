package com.example.movie.screen.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.usecase.GetMovieDetailByIdUC
import com.example.movie.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MovieDetailVM @Inject constructor(
    private val getMovieDetailByIdUC: GetMovieDetailByIdUC,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val uiState: MutableState<MovieDetailUIState> = mutableStateOf(MovieDetailUIState.Loading)
    var movieId by Delegates.notNull<Int>() // same as lateinit var

    init {
        movieId = savedStateHandle.toRoute<MovieDetailRoute>().id
//        loadMovieDetail()
    }
    fun loadMovieDetail() {
        uiState.value = MovieDetailUIState.Loading
        viewModelScope.launch {
            val result = getMovieDetailByIdUC.execute(movieId)
            uiState.value = when (result) {
                is Result.Success -> MovieDetailUIState.Success(result.data)
                is Result.Loading -> MovieDetailUIState.Loading
                is Result.Error -> MovieDetailUIState.Failure
            }
        }
    }
}

sealed interface MovieDetailUIState {
    data class Success(val data: MovieDetail): MovieDetailUIState
    data object Failure: MovieDetailUIState
    data object Loading: MovieDetailUIState
}