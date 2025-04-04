package com.example.movie.screen.movie

import android.R
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.CastMember
import com.example.movie.domain.model.MovieAccountState
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.model.Video
import com.example.movie.domain.repo.UserRepo
import com.example.movie.domain.usecase.GetCastsMovieUC
import com.example.movie.domain.usecase.GetMovieDetailByIdUC
import com.example.movie.domain.usecase.GetVideosMovieUC
import com.example.movie.domain.usecase.user.GetMovieAccountStateUC
import com.example.movie.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MovieDetailVM @Inject constructor(
    private val getMovieDetailByIdUC: GetMovieDetailByIdUC,
    private val getVideosMovieUC: GetVideosMovieUC,
    private val getCastsMovieUC: GetCastsMovieUC,
    private val getMovieAccountStateUC: GetMovieAccountStateUC,
    private val userRepo: UserRepo,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val uiState: MutableState<MovieDetailUIState> = mutableStateOf(MovieDetailUIState.Loading)

    val videoUiState: MutableState<VideoUIState> = mutableStateOf(VideoUIState.Loading)

    val castUiState: MutableState<CastUIState> = mutableStateOf(CastUIState.Loading)

    var movieId by Delegates.notNull<Int>() // same as lateinit var
        private set

    val showVideo = mutableStateOf(false)

    val currentVideoKey = mutableStateOf<String?>(null)

    private var movieAccountState = MutableStateFlow<MovieAccountState?>(null)

    private val _favorite = movieAccountState
        .map { it?.favorite }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )
    val favorite: StateFlow<Boolean?> = _favorite

    private val _watchlist = movieAccountState
        .map { it?.watchlist }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )


    val watchlist: StateFlow<Boolean?> = _watchlist

    private val _rating  = movieAccountState
        .map { it?.rated }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    val rating: StateFlow<Int?> = _rating

    init {
        movieId = savedStateHandle.toRoute<MovieDetailRoute>().id
        loadMovieDetail()
        loadVideos()
        loadCasts()
        loadMovieAccountState()
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
                is Result.Error -> MovieDetailUIState.Error
            }
        }
    }
    fun loadMovieAccountState() {
        viewModelScope.launch {
            val result = getMovieAccountStateUC.execute(movieId)
            when(result) {
                is Result.Success -> {
                    movieAccountState.value = result.data
                }
                is Result.Error -> {
                    // show error
                }
                else -> { }
            }
        }
    }

//    fun favoriteAction() {
//        viewModelScope.launch {
//            val result = userRepo.addToFavorite(accountId = , )
//        }
//    }

    fun loadVideos() {
        videoUiState.value = VideoUIState.Loading
        viewModelScope.launch {
            val result = getVideosMovieUC.execute(movieId)
            videoUiState.value = when(result) {
                is Result.Success -> VideoUIState.Success(result.data)
                is Result.Error -> VideoUIState.Error
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
                is Result.Error -> CastUIState.Error
                is Result.Loading -> CastUIState.Loading
            }
        }
    }
}

sealed interface MovieDetailUIState {
    data class Success(val data: MovieDetail): MovieDetailUIState
    data object Error: MovieDetailUIState
    data object Loading: MovieDetailUIState
}

sealed interface VideoUIState {
    data class Success(val data: List<Video>): VideoUIState
    data object Error: VideoUIState
    data object Loading: VideoUIState
}
sealed interface CastUIState {
    data class Success(val data: List<CastMember>): CastUIState
    data object Error: CastUIState
    data object Loading: CastUIState
}
