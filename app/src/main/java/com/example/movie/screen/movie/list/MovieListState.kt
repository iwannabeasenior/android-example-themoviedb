package com.example.movie.screen.movie.list

import com.example.model.model.Movie
import com.example.movie.base.ViewState

data class MovieListState (
    val movies: MutableList<Movie> = mutableListOf(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
): ViewState


enum class ProgressBarState {
    Idle,
    Half,
    Full
}