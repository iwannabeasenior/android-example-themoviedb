package com.example.movie.screen.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.CastMember
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.model.Video
import com.example.movie.domain.usecase.GetCastsMovieUC
import com.example.movie.domain.usecase.GetMovieDetailByIdUC
import com.example.movie.domain.usecase.GetVideosMovieUC
import com.example.movie.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MovieDetailVM @Inject constructor(
    private val getMovieDetailByIdUC: GetMovieDetailByIdUC,
    private val getVideosMovieUC: GetVideosMovieUC,
    private val getCastsMovieUC: GetCastsMovieUC,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val uiState: MutableState<MovieDetailUIState> = mutableStateOf(MovieDetailUIState.Loading)

    val videoUiState: MutableState<VideoUIState> = mutableStateOf(VideoUIState.Loading)

    val castUiState: MutableState<CastUIState> = mutableStateOf(CastUIState.Loading)

    var movieId by Delegates.notNull<Int>() // same as lateinit var
        private set

    val showVideo = mutableStateOf(false)

    val currentVideoKey = mutableStateOf<String?>(null)

    init {
        movieId = savedStateHandle.toRoute<MovieDetailRoute>().id
        loadMovieDetail()
        loadVideos()
        loadCasts()
    }

    fun showVideo(isShow: Boolean) {
        showVideo.value = isShow
    }

    fun changeCurrentKey(currentKey: String?) {
        currentVideoKey.value = currentKey
    }

    fun loadMovieDetail() {
        uiState.value = MovieDetailUIState.Loading
        viewModelScope.launch {
            val result = getMovieDetailByIdUC.execute(movieId)
            uiState.value = when (result) {
                is Result.Success -> MovieDetailUIState.Success(result.data)
                is Result.Loading -> MovieDetailUIState.Loading
                is Result.Error -> MovieDetailUIState.Failure
            }
        }
    }

    fun loadVideos() {
        videoUiState.value = VideoUIState.Loading
        viewModelScope.launch {
            val result = getVideosMovieUC.execute(movieId)
            videoUiState.value = when(result) {
                is Result.Success -> VideoUIState.Success(result.data)
                is Result.Error -> VideoUIState.Failure
                is Result.Loading -> VideoUIState.Loading
            }
        }
    }

    fun loadCasts() {
        castUiState.value = CastUIState.Loading
        viewModelScope.launch {
            val result = getCastsMovieUC.execute(movieId)
            castUiState.value = when(result) {
                is Result.Success -> CastUIState.Success(result.data)
                is Result.Error -> CastUIState.Failure
                is Result.Loading -> CastUIState.Loading
            }
        }
    }
}

sealed interface MovieDetailUIState {
    data class Success(val data: MovieDetail): MovieDetailUIState
    data object Failure: MovieDetailUIState
    data object Loading: MovieDetailUIState
}

sealed interface VideoUIState {
    data class Success(val data: List<Video>): VideoUIState
    data object Failure: VideoUIState
    data object Loading: VideoUIState
}
sealed interface CastUIState {
    data class Success(val data: List<CastMember>): CastUIState
    data object Failure: CastUIState
    data object Loading: CastUIState
}
