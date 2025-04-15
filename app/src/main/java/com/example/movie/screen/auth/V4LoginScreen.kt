package com.example.movie.screen.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.response.V4RequestAccessTokenResponse
import com.example.movie.LocalIsAuthen
import com.example.movie.datastore.UserPreferences
import com.example.movie.extention.openWebViewWithUrl
import com.example.movie.ui.MovieAppState
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.LinkUtil
import kotlinx.coroutines.launch

private fun AuthenWebView(url: String, context: Context) {
    context.openWebViewWithUrl(url)
}

@Composable
fun V4LoginScreen(vm: V4LoginVM = hiltViewModel(), appState: MovieAppState, onNavigateToMainRoute: () -> Unit) {

    val requestToken = vm.requestToken

    val context = LocalContext.current

    val isAuthen = LocalIsAuthen.current

    val accessTokenUIState = vm.accessTokenUIState.collectAsStateWithLifecycle()

    LaunchedEffect(requestToken.value) {
        if (requestToken.value != null) {
            val url = LinkUtil.createUserPermissionURLV4(requestToken = requestToken.value!!)
            AuthenWebView(url, context)
        }
    }

    LaunchedEffect(isAuthen) {
        if (isAuthen == true) {
            vm.createAccessToken()
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { vm.requestToken(redirectTo = "mymovie://auth/callback")
            },
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(30))
                .background(PurpleMovie)
        ) {
            Text(
                "Login",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    when(accessTokenUIState.value) {
        is AccessTokenUIState.Waiting -> CircularProgressIndicator()
        is AccessTokenUIState.Success -> {
            appState.coroutineScope.launch {
                saveUserInfo(appState.userPreferences, (accessTokenUIState.value as AccessTokenUIState.Success).data)
                appState.changeAuthState(false)
            }
        }
        is AccessTokenUIState.Error -> {

        }
    }
}

private suspend fun saveUserInfo(userPreferences: UserPreferences, data: V4RequestAccessTokenResponse) {
    userPreferences.saveAccessToken(accessToken = data.accessToken)
    userPreferences.saveAccountId(accountId = data.accountId)
}