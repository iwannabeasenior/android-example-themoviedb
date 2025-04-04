package com.example.movie.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.movie.datastore.UserPreferences
import com.example.movie.navigation.TopLevelDestination
import com.example.movie.navigation.navigateToActor
import com.example.movie.navigation.navigateToHome
import com.example.movie.navigation.navigateToMe
import com.example.movie.navigation.navigateToMovie
import com.example.movie.utils.NetWorkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber


@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController(),
    netWorkMonitor: NetWorkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    userPreferences: UserPreferences,
    changeAuthState: (Boolean) -> Unit
): MovieAppState {
    return remember (
        navController
    ) {
        MovieAppState(
            navController = navController,
            netWorkMonitor = netWorkMonitor,
            coroutineScope = coroutineScope,
            userPreferences = userPreferences,
            changeAuthState = changeAuthState
        )
    }
}

class MovieAppState (
    val navController: NavHostController,
    val netWorkMonitor: NetWorkMonitor,
    val coroutineScope: CoroutineScope,
    val userPreferences: UserPreferences,
    val changeAuthState: (Boolean) -> Unit
) {
    // difference with normal state flow is that it can stop collect data from flow when no one subscribe, state flow is stop state.
    val isOffline = netWorkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            /* delay 5s before stopping flow when last subscriber disappears
             * 2 other type: SharingStarted.Eagerly && SharingStarted.Lazily
                */
            initialValue = false
        )

    val isFirstTime = userPreferences
        .isNotFirstTime()
        .map { FirstTimeState.Loaded(it != true) }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = FirstTimeState.Loading
        )
    val isLogin = userPreferences.getAccessToken()
        .map { LoginState.Loaded(!it.isNullOrEmpty()) }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = LoginState.Loading
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

    val isNotTopLevelDestination: Boolean
        @Composable
        get() = TopLevelDestination.entries.all { topLevelDestination ->
                currentDestination?.route != topLevelDestination.route.qualifiedName
            }


    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            Timber.d("Navigation: ${topLevelDestination.name}")
            val topLevelNavOptions = navOptions {
                // pop before navigate
                popUpTo(navController.graph.findStartDestination().id) { // always back to home and exit
                    saveState = true
//                    inclusive = true // pop matching ID , not pop any route between
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
    fun onBackPressed() {
        navController.popBackStack()
    }
}

sealed class FirstTimeState {
    object Loading: FirstTimeState()
    data class Loaded(val value: Boolean?): FirstTimeState()
}
sealed class LoginState {
    object Loading: LoginState()
    data class Loaded(val value: Boolean): LoginState()
}