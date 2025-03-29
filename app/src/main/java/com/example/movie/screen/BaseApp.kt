package com.example.movie.screen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movie.R
import com.example.movie.navigation.LoginRoute
import com.example.movie.navigation.MainRoute
import com.example.movie.navigation.MovieNavHost
import com.example.movie.navigation.SplashRoute
import com.example.movie.ui.FirstTimeState
import com.example.movie.ui.LoginState
import com.example.movie.ui.MovieAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlin.reflect.KClass


@Composable
fun BaseApp(appState: MovieAppState) {

    var isFirstTime: MutableState<Boolean?> = rememberSaveable {
        mutableStateOf(null)
    }

    val isLogin: MutableState<Boolean?> = rememberSaveable {
        mutableStateOf(null)
    }

    var startDestination: MutableState<KClass<*>> = remember {
        mutableStateOf(SplashRoute::class)
    }

    LaunchedEffect(true) {

        isFirstTime.value = (appState.isFirstTime
            .filterNot { it == FirstTimeState.Loading }
            .first() as FirstTimeState.Loaded)
            .value
        isLogin.value = (appState.isLogin
            .filterNot { it == LoginState.Loading }
            .first() as LoginState.Loaded)
            .value

        startDestination.value = if (isFirstTime.value == true) {
            SplashRoute::class
        } else {
//            isLogin.value?.let {
//                if ((it as LoginState.Loaded).value) {
//                    MainRoute::class
//                } else {
//                    LoginRoute::class
//                }
//            }
//            LoginRoute::class
            if (isLogin.value == true) {
                MainRoute::class
            } else {
                LoginRoute::class
            }
        }
        appState.userPreferences.saveIsNotFirstTime()
    }

    val isOffline = appState.isOffline.collectAsStateWithLifecycle()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val notConnectedMessage = stringResource(R.string.not_connected)

    LaunchedEffect(isOffline) {
        if (isOffline.value) {
            snackBarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (isFirstTime.value != null) {
            MovieNavHost(appState = appState, startDestination = startDestination.value)
        } else {
            CircularProgressIndicator()
        }
    }

}