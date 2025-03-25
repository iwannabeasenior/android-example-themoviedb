package com.example.movie.navigation

import com.example.movie.R
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        icon = R.drawable.ic_home,
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class
    ),
    MOVIE(
        icon = R.drawable.ic_movie,
        route = MovieRoute::class,
        baseRoute = MovieBaseRoute::class
    ),
    ACTOR(
        icon = R.drawable.ic_actor,
        route = ActorRoute::class,
        baseRoute = ActorBaseRoute::class
    ),
    ME(
        icon = R.drawable.ic_me,
        route = MeRoute::class,
        baseRoute = MeBaseRoute::class
    )
}

