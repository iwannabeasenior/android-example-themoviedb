package com.example.movie.screen.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie.LocalIsAuthen
import com.example.movie.extention.openWebViewWithUrl
import com.example.movie.ui.MovieAppState
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.LinkUtil
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun LoginScreen(vm: LoginVM = hiltViewModel(), appState: MovieAppState, onNavigateToMainRoute: () -> Unit) {

    val uiState by vm.uiState.collectAsStateWithLifecycle()

    val requestSessionState by vm.uiSessionState.collectAsStateWithLifecycle()

    val value = LocalIsAuthen.current

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState, requestSessionState) {
        handleUiStateChanges(
            uiState = uiState,
            requestSessionState = requestSessionState,
            appState = appState,
            snackBarHostState = snackBarHostState,
            onNavigateToMainRoute = onNavigateToMainRoute
        )
    }


    LaunchedEffect(value) {
        if (value == true) {
            vm.requestSession()
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PurpleMovie),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginPrompt {
                vm.requestToken()
            }
        }

        HandleUiState(modifier = Modifier.align(Alignment.Center), uiState = uiState)

        HandleSessionState(modifier = Modifier.align(Alignment.Center), requestSessionState = requestSessionState, onNavigateToMainRoute = onNavigateToMainRoute)

    }
}
@Composable
private fun HandleUiState(modifier: Modifier = Modifier, uiState: LoginUiState?) {
    when (uiState) {
        is LoginUiState.Success -> {
            val url = LinkUtil.createUserPermissionURL(requestToken = uiState.token)
            AuthenWebView(url)
        }
        is LoginUiState.Loading -> CircularProgressIndicator(modifier = Modifier.then(modifier))
        is LoginUiState.Error -> {}  // Can show an error message if needed
        else -> {}
    }
}
@Composable
private fun HandleSessionState(
    modifier: Modifier = Modifier,
    requestSessionState: RequestSessionState?,
    onNavigateToMainRoute: () -> Unit
) {
    when (requestSessionState) {
        is RequestSessionState.Success -> onNavigateToMainRoute()
        is RequestSessionState.Loading -> CircularProgressIndicator(modifier = Modifier.then(modifier))
        is RequestSessionState.Error -> {}  // Can show an error message if needed
        else -> {}
    }
}
@Composable
private fun LoginPrompt(onLoginClick: () -> Unit) {
    Text(
        "You have not yet to log in, Log In ------>",
        fontSize = 20.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(bottom = 20.dp)
    )
    TextButton(
        onClick = onLoginClick,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
    ) {
        Text("Log In", fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }

}
@Composable
private fun AuthenWebView(url: String) {
    val context = LocalContext.current
    context.openWebViewWithUrl(url)
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebViewScreen(url: String) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}


private suspend fun handleUiStateChanges(
    uiState: LoginUiState?,
    requestSessionState: RequestSessionState?,
    appState: MovieAppState,
    snackBarHostState: SnackbarHostState,
    onNavigateToMainRoute: () -> Unit
) {
    if (uiState is LoginUiState.Error || requestSessionState is RequestSessionState.Error) {
        val message = "Authentication failed, Please try again"
        snackBarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
    }
    if (requestSessionState is RequestSessionState.Success) {
        appState.userPreferences.saveSessionId(requestSessionState.sessionID)
        onNavigateToMainRoute()
    }
}