package com.example.movie.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.movie.screen.home.HomeScreen
import com.example.movie.screen.movie.MovieDetailScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

@Composable
fun MovieNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: KClass<*> = HomeBaseRoute::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation<HomeBaseRoute>(
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen { id ->
                    navController.navigate(MovieDetailRoute(id))
                }
            }
            composable<MovieDetailRoute> { entry ->
                MovieDetailScreen()
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

            }
        }
    }

}