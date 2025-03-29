package com.example.movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.movie.screen.SplashScreen
import com.example.movie.screen.actor.PersonDetailScreen
import com.example.movie.screen.auth.LoginScreen
import com.example.movie.screen.home.HomeScreen
import com.example.movie.screen.movie.MovieDetailScreen
import com.example.movie.screen.profile.ProfileScreen
import com.example.movie.ui.MovieApp
import com.example.movie.ui.MovieAppState
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

@Composable
fun MovieNavHost(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    appState: MovieAppState,
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<*> = SplashRoute::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<SplashRoute> { SplashScreen { navController.navigateToLogin() } }

        composable<LoginRoute> {
            LoginScreen(appState = appState) {
                navController.navigateToMainRoute(
                    navOptions = navOptions {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                )
            }
        }
        composable<MainRoute> {
            MovieApp(appState = appState) {
                navController.navigateToLogin(navOptions = navOptions {
                    popUpTo(LoginRoute) {
                        inclusive = false
                    }
                })
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    appState: MovieAppState,
    startDestination: KClass<*> = HomeBaseRoute::class,
    onNavigateToLogin: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<HomeBaseRoute>(
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen { id ->
                    navController.navigateToMovieDetailById(id)
                }
            }
            composable<MovieDetailRoute> { entry ->
                MovieDetailScreen { personId ->
                    navController.navigateToPersonDetailById(personId)
                }
            }
            composable<PersonDetailRoute> { entry ->
                PersonDetailScreen { movieId ->
                    navController.navigateToMovieDetailById(movieId)
                }
            }
        }
        navigation<MovieBaseRoute>(
            startDestination = MovieRoute
        ) {
            composable<MovieRoute> {

            }
        }

        navigation<ActorBaseRoute>(
            startDestination = ActorRoute
        ) {
            composable<ActorRoute> {

            }
        }

        navigation<MeBaseRoute>(
            startDestination = MeRoute
        ) {
            composable<MeRoute> {
                ProfileScreen(appState = appState) {
                    onNavigateToLogin()
                }
            }
        }

    }
}
