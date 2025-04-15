package com.example.movie.screen.movie.list

import androidx.lifecycle.viewModelScope
import com.example.common.base.Result
import com.example.domain.repo.MovieRepo
import com.example.domain.usecase.GetMovieGenresUC
import com.example.model.model.Genre
import com.example.model.model.Movie
import com.example.model.request.DiscoverMoviesParams
import com.example.model.request.Order
import com.example.model.request.SortBy
import com.example.movie.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// solution first , clean code after
// code by my way

@HiltViewModel
class MovieVM @Inject constructor(
    private val movieRepo: MovieRepo,
    private val getMovieGenresUC: GetMovieGenresUC
): BaseViewModel<MovieListEvent, MovieListState, Nothing>() {

    override fun setInitialState(): MovieListState {
        return MovieListState()
    }

    override fun onTriggerEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.GetNewData -> {

            }

            is MovieListEvent.Idle -> { }

            else -> {

            }
        }
    }


    fun getMovies(
        sort_by: String = SortBy.popularity.fromOrder(Order.desc),
        vote_count_lte: Float? = null,
        vote_count_gte: Float? = null,
        with_runtime_gte: Int? = null,
        with_runtime_lte: Int? = null,
        with_genres: String? = null,
        with_keywords: String? = null
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val result = movieRepo.discoverMovies(DiscoverMoviesParams(
                    sort_by = sort_by,
                    vote_count_lte = vote_count_lte,
                    vote_count_gte = vote_count_gte,
                    with_runtime_gte = with_runtime_gte,
                    with_runtime_lte = with_runtime_lte,
                    with_genres = with_genres,
                    with_keywords = with_keywords
                ))

                when(result) {

                }
            }
        }
    }

    init {
        getGenres()
        getMovies()
    }

    fun getGenres() {
        viewModelScope.launch {
            val result = getMovieGenresUC.execute()
        }
    }
}

sealed interface GenresMovieUiState {
    data class Success(val genres: List<Genre>): GenresMovieUiState
    object Loading: GenresMovieUiState
    object LoadFailed: GenresMovieUiState
    object Empty: GenresMovieUiState
}


sealed interface MovieUIState {
    data class Success(val movies: List<Movie>): MovieUIState
    data object Error: MovieUIState
    data object Loading: MovieUIState
    object Empty: MovieUIState
}