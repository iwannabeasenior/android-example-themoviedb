package com.example.movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.movie.screen.SplashScreen
import com.example.movie.screen.actor.PersonDetailScreen
import com.example.movie.screen.auth.V4LoginScreen
import com.example.movie.screen.home.HomeScreen
import com.example.movie.screen.movie.detail.MovieDetailScreen
import com.example.movie.screen.movie.list.MovieScreen
import com.example.movie.screen.profile.ProfileScreen
import com.example.movie.screen.profile.subscreen.favorite.MyFavoriteScreen
import com.example.movie.screen.profile.subscreen.lists.MyListsScreen
import com.example.movie.screen.profile.subscreen.rated.MyRatedScreen
import com.example.movie.screen.profile.subscreen.watchlist.MyWatchListScreen
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
        modifier = modifier,
    ) {
        composable<SplashRoute> { SplashScreen(appState) { navController.navigateToLogin() } }

        composable<LoginRoute> {
            V4LoginScreen(appState = appState) {
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
                HomeScreen(navigateToMovieDetail = navController::navigateToMovieDetailById)
            }

            composable<MovieDetailRoute> { entry ->
                MovieDetailScreen(navigateToPersonDetail = navController::navigateToPersonDetailById)
            }

            composable<PersonDetailRoute> { entry ->
                PersonDetailScreen(onNavigateToMovieDetail = navController::navigateToMovieDetailById)
            }
        }
        navigation<MovieBaseRoute>(
            startDestination = MovieRoute
        ) {
            composable<MovieRoute> {
                MovieScreen(appState = appState)
            }
        }

        navigation<ActorBaseRoute>(
            startDestination = ActorRoute
        ) {
            composable<ActorRoute> {

            }
        }

        navigation<ProfileBaseRoute>(
            startDestination = ProfileRoute
        ) {
            composable<ProfileRoute> {
                ProfileScreen(appState = appState, onNavigateFavoriteList = navController::navigateToFavoriteList)
            }

            composable<FavoriteListRoute> {
                MyFavoriteScreen(onMovieClick = navController::navigateToMovieDetailById)
            }

            composable<ListsListRoute> {
                MyListsScreen()
            }

            composable<WatchListRoute> {
                MyWatchListScreen()
            }

            composable<RatedListRoute> {
                MyRatedScreen()
            }

        }

    }
}
