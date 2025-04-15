package com.example.movie.designpattern.smallcomponent

import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.example.common.ErrorType
import com.example.common.ErrorType.HttpException
import com.example.common.ErrorType.NetWorkError
import com.example.common.ErrorType.Unauthorized
import com.example.common.UIComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

suspend fun showSnackBarWithTitle(uiComponent: UIComponent.ToastSimple, snackBarHostState: SnackbarHostState, action: () -> Unit) {

    val title = when (val error = uiComponent.errorType) {
        is NetWorkError -> "Network Error, try again."
        is Unauthorized -> "You are unauthorized, please authorize before use service"
        is HttpException-> "Error happened in your request, try again"
        is ErrorType.UnknownError -> "Something happened, try again"
    }
    val result = snackBarHostState.showSnackbar(
        message = title,
        actionLabel = "Dismiss",
        withDismissAction = true,
        duration = SnackbarDuration.Indefinite,
    )
    when (result) {
        SnackbarResult.ActionPerformed -> {
            action()
        }

        SnackbarResult.Dismissed -> {
            action()
        }
    }
}