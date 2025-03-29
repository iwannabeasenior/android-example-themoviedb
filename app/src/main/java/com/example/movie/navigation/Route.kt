package com.example.movie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(
    route = HomeRoute,
    navOptions = navOptions
)

@Serializable
data object HomeBaseRoute

@Serializable
data object MovieRoute
fun NavController.navigateToMovie(navOptions: NavOptions) = navigate(
    route = MovieRoute,
    navOptions = navOptions
)

@Serializable
data object MovieBaseRoute

@Serializable
data object ActorRoute
fun NavController.navigateToActor(navOptions: NavOptions) = navigate(
    route = ActorRoute,
    navOptions = navOptions
)

@Serializable
data object ActorBaseRoute

@Serializable
data object MeRoute

fun NavController.navigateToMe(navOptions: NavOptions) = navigate(
    route = MeRoute,
    navOptions = navOptions
)

@Serializable
data object MeBaseRoute

@Serializable
data class MovieDetailRoute(val id: Int)

fun NavController.navigateToMovieDetailById(id: Int, navOptions: NavOptions? = null) = navigate(
    MovieDetailRoute(id),
    navOptions
)

@Serializable
data class PersonDetailRoute(val id: Int)

fun NavController.navigateToPersonDetailById(id: Int, navOptions: NavOptions? = null) = navigate(
    PersonDetailRoute(id),
    navOptions
)


@Serializable
data object SplashRoute
fun NavController.navigateToSplash(navOptions: NavOptions? = null) = navigate(SplashRoute)

@Serializable
data object LoginRoute
fun NavController.navigateToLogin(navOptions: NavOptions? = null) = navigate(LoginRoute)

@Serializable
data object MainRoute
fun NavController.navigateToMainRoute(navOptions: NavOptions? = null) = navigate(MainRoute)