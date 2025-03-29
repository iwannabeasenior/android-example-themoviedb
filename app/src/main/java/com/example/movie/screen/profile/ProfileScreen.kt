package com.example.movie.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movie.ui.MovieAppState
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(appState: MovieAppState, onNavigateToLogin: () -> Unit) {

    val scope = rememberCoroutineScope()

    fun logout() {
        scope.launch {
            appState.userPreferences.deleteSessionId()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        TextButton(
            modifier = Modifier.size(height = 50.dp, width = 150.dp).align(Alignment.Center),
            onClick = {
                logout()
                onNavigateToLogin()
            }
        ) {
            Text("Log out", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }
    }
}