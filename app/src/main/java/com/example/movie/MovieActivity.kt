package com.example.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.movie.screen.SplashScreen
import com.example.movie.screen.home.HomeScreen
import com.example.movie.ui.MovieApp
import com.example.movie.ui.MovieAppState
import com.example.movie.ui.rememberMovieAppState
import com.example.movie.ui.theme.MovieTheme
import com.example.movie.utils.NetWorkMonitor
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var netWorkMonitor: NetWorkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { // Inside Composable
            val appState = rememberMovieAppState(
                navController = rememberNavController(),
                netWorkMonitor = netWorkMonitor,
                coroutineScope = rememberCoroutineScope()
            )
            MovieTheme {
                MovieApp(appState = appState)
            }
        }
    }
}
