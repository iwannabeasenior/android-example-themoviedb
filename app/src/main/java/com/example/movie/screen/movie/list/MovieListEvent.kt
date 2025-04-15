package com.example.movie.screen.movie.list

import com.example.movie.base.ViewEvent

sealed class MovieListEvent : ViewEvent {
    data object OnRetryNetwork : ViewEvent
    data object GetNewData: ViewEvent
    data object Idle: ViewEvent

}