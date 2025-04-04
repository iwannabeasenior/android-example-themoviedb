package com.example.movie.screen.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.base.Result
import com.example.movie.domain.usecase.user.RequestSessionUC
import com.example.movie.domain.usecase.user.RequestTokenUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val requestTokenUC: RequestTokenUC,
    private val requestSessionUC: RequestSessionUC
): ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<LoginUiState?> = _uiState

    private val _uiSessionState: MutableStateFlow<RequestSessionState?> = MutableStateFlow(null)
    val uiSessionState: StateFlow<RequestSessionState?> = _uiSessionState

    val showWebView = mutableStateOf(false)

    var token: String = ""

    fun requestToken() {
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val result = requestTokenUC.execute()
            _uiState.value = when (result) {
                is Result.Success -> {
                    token = result.data.requestToken!!
                    LoginUiState.Success(result.data.requestToken)
                }

                is Result.Loading -> LoginUiState.Loading
                is Result.Error -> LoginUiState.Error
            }
        }
    }
    fun requestSession() {
        viewModelScope.launch {
            _uiSessionState.value = RequestSessionState.Loading
            val result = requestSessionUC.execute(token)
            _uiSessionState.value = when (result) {
                is Result.Success -> {
                    val isSuccess = result.data.success

                    if (isSuccess) {
                        RequestSessionState.Success(result.data.sessionID!!)
                    } else {
                        RequestSessionState.Error
                    }
                }
                is Result.Error -> {
                    RequestSessionState.Error
                }
                is Result.Loading -> {
                    RequestSessionState.Loading
                }
            }
        }
    }
}

interface LoginUiState {
    data class Success(val token: String) : LoginUiState
    data object Error : LoginUiState
    data object Loading : LoginUiState
}
interface RequestSessionState {
    data class Success(val sessionID: String) : RequestSessionState
    data object Error : RequestSessionState
    data object Loading : RequestSessionState
}