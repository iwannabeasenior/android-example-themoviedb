package com.example.movie.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.navOptions
import com.example.movie.R
import com.example.movie.navigation.MovieNavHost
import com.example.movie.navigation.TopLevelDestination
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.PurpleMovie
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(modifier: Modifier = Modifier, appState: MovieAppState) {
    val currentDestination = appState.currentDestination
    val isOffline = appState.isOffline.collectAsStateWithLifecycle()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline) {
        Log.d("is_offline", isOffline.value.toString())
        if (isOffline.value) {
            snackBarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = PurpleMovie,
                contentColor = Color.White
            ) {
                TopLevelDestination.entries.forEachIndexed { index, destination ->
                    NavigationBarItemWrapper(destination = destination, currentDestination = currentDestination) {
                        appState.navigateToTopLevelDestination(destination)
                    }
                }
            }
        },
        topBar = {
            if (appState.isNotTopLevelDestination) {
                TopAppBar(
                    title = {
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                appState.onBackPressed()
                            }
                        ) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = GreenMovie)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MovieNavHost(navController = appState.navController)
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this?.hierarchy?.any {
        it.hasRoute(route)
    } == true
}

@Composable
private fun RowScope.NavigationBarItemWrapper(destination: TopLevelDestination, currentDestination: NavDestination?, onClick: () -> Unit) {
    val selected by remember(currentDestination) {
        derivedStateOf { currentDestination?.isRouteInHierarchy(destination.baseRoute) == true }
    }

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(destination.icon),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
    )
}