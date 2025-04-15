package com.example.movie.error

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.notify

class ErrorNotifier {
    private val _errorFlow = Channel<ErrorNotification>()
    val errorFlow: Flow<ErrorNotification?> = _errorFlow.receiveAsFlow()

    fun notify(errorNotification: ErrorNotification?) {
        _errorFlow
    }
}