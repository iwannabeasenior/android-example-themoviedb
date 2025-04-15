package com.example.movie.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.common.UIComponent
import com.example.movie.designpattern.smallcomponent.ShowDialog
import com.example.movie.designpattern.smallcomponent.showSnackBarWithTitle
import com.example.movie.screen.movie.list.MovieUIState
import com.example.movie.ui.MovieAppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


@Composable
fun DefaultScreenUI(
    uiState: MovieAppState,
    errors: Flow<UIComponent> = MutableSharedFlow(),
    content: @Composable () -> Unit,
) {

    val errorQueue = remember {
        mutableStateOf<Queue<UIComponent>>(Queue(mutableListOf()))
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
//        topBar = {
//            if (titleToolbar != null) {
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    if (startIconToolbar != null) {
//                        CircleButton(
//                            imageVector = startIconToolbar,
//                            onClick = { onClickStartIconToolbar() })
//                    } else {
//                        Spacer_16dp()
//                    }
//                    Text(titleToolbar, style = MaterialTheme.typography.titleLarge)
//
//                    if (endIconToolbar != null) {
//                        CircleButton(
//                            imageVector = endIconToolbar,
//                            onClick = { onClickEndIconToolbar() })
//                    } else {
//                        Spacer_16dp()
//                    }
//                }
//            }
//        }
    ) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            content()


            LaunchedEffect(errors) {
                errors.collect { errors ->
                    errorQueue.appendToMessageQueue(errors)
                }
            }


            // process the queue
            if (!errorQueue.value.isEmpty()) {
                errorQueue.value.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.DialogSimple) {

                        ShowDialog(
                            modifier = Modifier.align(
                                Alignment.Center
                            ),
                            uiComponent = uiComponent,
                            onDismissRequest = {
                                errorQueue.removeHeadMessage()
                            }
                        )
                    }
                    if(uiComponent is UIComponent.ToastSimple) {
                        uiState.coroutineScope.launch {
                            showSnackBarWithTitle(
                                uiComponent = uiComponent,
                                snackBarHostState = snackBarHostState,
                                action = {
                                    errorQueue.removeHeadMessage()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


private fun MutableState<Queue<UIComponent>>.appendToMessageQueue(uiComponent: UIComponent) {
    if (uiComponent is UIComponent.None) {
        return
    }

    val queue = this.value
    queue.add(uiComponent)

    this.value = Queue(mutableListOf()) // force to recompose
    this.value = queue
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


@Composable
fun <Effect : ViewSingleAction> EffectHandler(
    effectFlow: Flow<Effect>,
    onHandleEffect: (Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            onHandleEffect(effect)
        }
    }
}
