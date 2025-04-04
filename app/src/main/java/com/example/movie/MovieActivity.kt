package com.example.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.movie.datastore.UserPreferences
import com.example.movie.extention.addTitleLog
import com.example.movie.screen.BaseApp
import com.example.movie.ui.rememberMovieAppState
import com.example.movie.ui.theme.MovieTheme
import com.example.movie.utils.NetWorkMonitor
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "auth",
    corruptionHandler = ReplaceFileCorruptionHandler<Preferences> { corruptionException ->
        Timber.d(addTitleLog("Read, Write data failed, error message is: ${corruptionException.message}"))
        emptyPreferences()
    } // when file failed, get new file
)

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

            LaunchedEffect(true) {
                userPreferences.startGetAccessToken()
            }

            val appState = rememberMovieAppState(
                navController = rememberNavController(),
                netWorkMonitor = netWorkMonitor,
                coroutineScope = rememberCoroutineScope(),
                userPreferences = userPreferences
            ) {
                _isAuthen.value = it
            }

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
                _isAuthen.value = true
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
