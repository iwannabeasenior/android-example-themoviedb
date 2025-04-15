package com.example.movie.screen.profile.subscreen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repo.UserRepo
import com.example.domain.usecase.user.GetUserFavoriteListUC
import com.example.model.model.Movie
import com.example.common.base.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoriteVM @Inject constructor(
    private val favoriteUC: GetUserFavoriteListUC,
    private val userRepo: UserRepo
): ViewModel() {
    val favoriteListUIState = MutableStateFlow<FavoriteListUIState>(FavoriteListUIState.Loading)
    val movies = MutableStateFlow<List<Movie>>(emptyList())
    init {
        fetchData()
    }
    fun fetchData() {
        viewModelScope.launch {
            favoriteListUIState.value = FavoriteListUIState.Loading
            val result = favoriteUC.execute()
            favoriteListUIState.value = when (result) {
                is Result.Success -> {
                    movies.value = result.data
                    FavoriteListUIState.Success(result.data.toMutableList())
                }
                is Result.Error -> FavoriteListUIState.Error
                is Result.Loading -> FavoriteListUIState.Loading
            }
        }
    }

    fun removeMovieFromList(movieType: String, movieId: Int) {
        viewModelScope.launch {
            val result = userRepo.addToFavorite(
                media_type = movieType,
                media_id = movieId,
                favorite = false
            )
            if (result.isSuccessful) {
                movies.value = movies.value.dropWhile { it.id == movieId }
            }
        }
    }
}

interface FavoriteListUIState {
    data class Success(val data: MutableList<Movie>): FavoriteListUIState {
        fun isEmpty(): Boolean {
            return data.isEmpty()
        }
        fun removeMovieFromList(movie: Movie) {
            data.remove(movie)
        }
    }
    data object Error: FavoriteListUIState
    data object Loading: FavoriteListUIState
}