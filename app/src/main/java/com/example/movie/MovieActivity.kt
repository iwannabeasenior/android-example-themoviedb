package com.example.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.movie.datastore.UserPreferences
import com.example.movie.screen.BaseApp
import com.example.movie.ui.rememberMovieAppState
import com.example.movie.ui.theme.MovieTheme
import com.example.movie.utils.NetWorkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

val LocalIsAuthen = compositionLocalOf { false }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var netWorkMonitor: NetWorkMonitor

    @Inject
    lateinit var userPreferences: UserPreferences

    private var _isAuthen = mutableStateOf(false)
    val isAuthen: Boolean get() = _isAuthen.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent { // Inside Composable
            val appState = rememberMovieAppState(
                navController = rememberNavController(),
                netWorkMonitor = netWorkMonitor,
                coroutineScope = rememberCoroutineScope(),
                userPreferences = userPreferences
            )

            CompositionLocalProvider(LocalIsAuthen provides isAuthen) {
                MovieTheme {
                    BaseApp(appState)
                }
            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data.let { uri ->
            if (uri?.scheme == SCHEME_NAME && uri.host == HOST_NAME && uri.path == PATH_NAME) {
                val approved = uri.getBooleanQueryParameter(APPROVED_PARAM, false)
                val denied = uri.getBooleanQueryParameter(DENIED_PARAM, false)
                _isAuthen.value = approved == true
            }
        }
    }

    companion object {
        const val SCHEME_NAME = "mymovie"
        const val HOST_NAME = "auth"
        const val PATH_NAME = "/callback"
        const val APPROVED_PARAM = "approved"
        const val DENIED_PARAM = "denied"
    }
}
