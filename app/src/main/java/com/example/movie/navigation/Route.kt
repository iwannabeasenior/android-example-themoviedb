package com.example.movie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions = navOptions)

@Serializable
data object HomeBaseRoute

@Serializable
data object MovieRoute
fun NavController.navigateToMovie(navOptions: NavOptions) = navigate(route = MovieRoute, navOptions = navOptions)

@Serializable
data object MovieBaseRoute

@Serializable
data object ActorRoute
fun NavController.navigateToActor(navOptions: NavOptions) = navigate(route = ActorRoute, navOptions = navOptions)

@Serializable
data object ActorBaseRoute

@Serializable
data object MeRoute
fun NavController.navigateToMe(navOptions: NavOptions) = navigate(route = MeRoute, navOptions = navOptions)

@Serializable
data object MeBaseRoute

//

@Serializable
data class MovieDetailRoute(val id: Int)