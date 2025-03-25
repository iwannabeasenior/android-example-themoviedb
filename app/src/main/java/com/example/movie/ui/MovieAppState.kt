package com.example.movie.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.movie.navigation.TopLevelDestination
import com.example.movie.navigation.navigateToActor
import com.example.movie.navigation.navigateToHome
import com.example.movie.navigation.navigateToMe
import com.example.movie.navigation.navigateToMovie
import com.example.movie.utils.NetWorkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber


@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController(),
    netWorkMonitor: NetWorkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MovieAppState{
    return remember (
        navController
    ) {
        MovieAppState(
            navController = navController,
            netWorkMonitor = netWorkMonitor,
            coroutineScope = coroutineScope
        )
    }
}

class MovieAppState (
    val navController: NavHostController,
    val netWorkMonitor: NetWorkMonitor,
    val coroutineScope: CoroutineScope
) {
    val isOffline = netWorkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )


    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryAsState()
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            Timber.d("Navigation: ${topLevelDestination.name}")
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true // restore state for any  for saveState in popUpToBuilder
            }
            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.MOVIE -> navController.navigateToMovie(topLevelNavOptions)
                TopLevelDestination.ACTOR -> navController.navigateToActor(topLevelNavOptions)
                TopLevelDestination.ME -> navController.navigateToMe(topLevelNavOptions)
            }
        }
    }
}