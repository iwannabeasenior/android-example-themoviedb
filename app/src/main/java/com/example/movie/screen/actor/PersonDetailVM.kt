package com.example.movie.screen.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie.data.response.CreditMoviesResponse
import com.example.movie.domain.model.PersonDetail
import com.example.movie.domain.usecase.GetPersonDetailUC
import com.example.movie.navigation.PersonDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates
import com.example.movie.domain.base.Result
import com.example.movie.domain.usecase.GetPersonMoviesUC

@HiltViewModel
class PersonDetailVM @Inject constructor(
    private val getPersonDetailUC: GetPersonDetailUC,
    private val getPersonMoviesUC: GetPersonMoviesUC,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<PersonDetailUiState> = MutableStateFlow(PersonDetailUiState.Loading)
    val uiState: StateFlow<PersonDetailUiState> = _uiState

    private val _uiKnownForState: MutableStateFlow<KnownForUiState> = MutableStateFlow(KnownForUiState.Loading)
    val uiKnownForUiState: StateFlow<KnownForUiState> = _uiKnownForState

    var personId by Delegates.notNull<Int>()

    init {
        personId = savedStateHandle.toRoute<PersonDetailRoute>().id
        loadPersonDetail()
        loadKnownFor()
    }
    fun loadPersonDetail() {
        viewModelScope.launch {
            val result = getPersonDetailUC.execute(personId)
            _uiState.value = when(result) {
                is Result.Error -> PersonDetailUiState.Error
                is Result.Loading -> PersonDetailUiState.Loading
                is Result.Success -> PersonDetailUiState.Success(result.data)
            }
        }
    }
    fun loadKnownFor() {
        viewModelScope.launch {
            val result = getPersonMoviesUC.execute(personId)
            _uiKnownForState.value = when (result) {
                is Result.Success -> KnownForUiState.Success(result.data)
                is Result.Loading -> KnownForUiState.Loading
                is Result.Error -> KnownForUiState.Error
            }
        }
    }
}

interface PersonDetailUiState {
    data class Success(val data: PersonDetail): PersonDetailUiState
    object Loading: PersonDetailUiState
    object Error: PersonDetailUiState
}

interface KnownForUiState {
    data class Success(val data: CreditMoviesResponse): KnownForUiState
    object Loading: KnownForUiState
    object Error: KnownForUiState
}


