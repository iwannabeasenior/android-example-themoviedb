package com.example.movie.di.network


import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME) // help annotation exist until runtime to hilt can use
annotation class Dispatcher(val movieDispatcher: MovieDispatchers)

enum class MovieDispatchers {
    Default,
    IO
}