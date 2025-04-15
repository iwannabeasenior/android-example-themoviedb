package com.example.movie.base

import com.example.common.UIComponent
import com.example.movie.di.coroutine.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ErrorQueue(
    private val scope: CoroutineScope
) {
    private val _errors: Channel<UIComponent> = Channel()
    val errors = _errors.receiveAsFlow()

    fun setError(builder: () -> UIComponent) {
        val effectValue = builder()
        scope.launch {
            _errors.send(effectValue)
        }
    }
}