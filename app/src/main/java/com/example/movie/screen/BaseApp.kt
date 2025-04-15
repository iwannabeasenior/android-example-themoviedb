package com.example.movie.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie.R
import com.example.movie.base.Queue
import com.example.common.UIComponent
import com.example.movie.designpattern.smallcomponent.ShowDialog
import com.example.movie.designpattern.smallcomponent.showSnackBarWithTitle
import com.example.movie.navigation.LoginRoute
import com.example.movie.navigation.MainRoute
import com.example.movie.navigation.MovieNavHost
import com.example.movie.navigation.SplashRoute
import com.example.movie.ui.FirstTimeState
import com.example.movie.ui.LoginState
import com.example.movie.ui.MovieAppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


@Composable
fun BaseApp(appState: MovieAppState) {

    var isFirstTime: MutableState<FirstTimeState> = remember {
        mutableStateOf(FirstTimeState.Loading)
    }

    var isLogin = appState.isLogin
        .filterNot { it == LoginState.Loading }
        .collectAsStateWithLifecycle(initialValue = null)

    var startDestination: MutableState<KClass<*>> = remember {
        mutableStateOf(SplashRoute::class)
    }
    suspend fun setFirstTimeValue() {
        isFirstTime.value = (appState.isFirstTime
            .filterNot { it == FirstTimeState.Loading }
            .first() as FirstTimeState.Loaded)
    }

    val errorQueue = remember { mutableStateOf(Queue<UIComponent>(mutableListOf())) }

    LaunchedEffect(Unit) {
        appState.errorQueue.errors.collect { error ->
            errorQueue.value = errorQueue.value.copy(error)
        }
    }
    LaunchedEffect(isLogin.value) {

        setFirstTimeValue()
        // Now isFirstTime has been loaded

        startDestination.value = if ((isFirstTime.value as FirstTimeState.Loaded).value == true) {
            SplashRoute::class
        } else {
            if ((isLogin.value as? LoginState.Loaded)?.value == true) {
                MainRoute::class
            } else {
                LoginRoute::class
            }
        }
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
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            when (isFirstTime.value) {
                is FirstTimeState.Loading ->
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is FirstTimeState.Loaded ->
                    MovieNavHost(appState = appState, startDestination = startDestination.value)
            }

            if (!errorQueue.value.isEmpty()) {
                errorQueue.value.peek().let { uiComponent ->
                    when (uiComponent) {
                        is UIComponent.DialogSimple ->
                            ShowDialog(
                                modifier = Modifier.align(
                                    Alignment.Center
                                ),
                                uiComponent = uiComponent,
                                onDismissRequest = {
                                    errorQueue.removeHeadMessage()
                                }
                            )
                        is UIComponent.ToastSimple -> {
                            appState.coroutineScope.launch(Dispatchers.Main) {
                                showSnackBarWithTitle(
                                    title = uiComponent.title,
                                    snackBarHostState = snackBarHostState,
                                    action = {
                                        errorQueue.removeHeadMessage()
                                    }
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

private fun MutableState<Queue<UIComponent>>.removeHeadMessage() {
    if (this.value.isEmpty()) {
        println("removeHeadMessage: Nothing to remove from DialogQueue")
        return
    }
    val queue = this.value
    queue.remove() // can throw exception if empty
    this.value = Queue(mutableListOf()) // force to recompose
    this.value = queue
}
