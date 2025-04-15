package com.example.movie.screen.movie.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.common.base.Result
import com.example.domain.repo.UserRepo
import com.example.domain.usecase.GetCastsMovieUC
import com.example.domain.usecase.GetMovieDetailByIdUC
import com.example.domain.usecase.GetVideosMovieUC
import com.example.domain.usecase.user.GetMovieAccountStateUC
import com.example.model.model.CastMember
import com.example.model.model.MovieAccountState
import com.example.model.model.MovieDetail
import com.example.model.model.Video
import com.example.movie.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val showRatingPicker = mutableStateOf(false)

    val currentVideoKey = mutableStateOf<String?>(null)

    var movieAccountState = MutableStateFlow<MovieAccountState?>(null)

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

                }
                else -> { }
            }
        }
    }




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

    fun addToFavorite(mediaType: String, mediaId: Int, favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.addToFavorite(
                media_type = mediaType,
                media_id = mediaId,
                favorite = favorite
            )
            withContext(Dispatchers.Main) {

                if (result.isSuccessful) {
                    if (result.body()?.success == true) {
                        movieAccountState.value = movieAccountState.value?.copy(favorite = favorite)
                    }
                } else {

                }
            }
        }
    }

    fun addToWatchlist(mediaType: String, mediaId: Int, watchlist: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.addToWatchList(
                media_type = mediaType,
                media_id = mediaId,
                watch_list = watchlist
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    if (result.body()?.success == true) {
                        movieAccountState.value = movieAccountState.value?.copy(watchlist = watchlist)
                    } else {
                        // notification that error happened
                    }
                }
            }
        }
    }

    fun onActionRating(rating: Int) {
        if (rating == 0) {
            if (movieAccountState.value?.rated != null) {
                deleteRating()
            }
        } else if (rating != movieAccountState.value?.rated) {
            addRating(rating)
        }
    }

    fun addRating(rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.addRating(movieId, rating)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful && result.body()?.success == true) {
                    movieAccountState.value = movieAccountState.value?.copy(rated = rating)
                }
            }
        }
    }
    fun deleteRating() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.deleteRating(movieId)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful && result.body()?.success == true) {
                    movieAccountState.value = movieAccountState.value?.copy(rated = null)
                }
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
