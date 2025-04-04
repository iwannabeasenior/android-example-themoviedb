package com.example.movie.screen.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.data.response.V4RequestAccessTokenResponse
import com.example.movie.domain.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class V4LoginVM @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {
    var requestToken = mutableStateOf<String?>(null)

    private var _accessTokenUIState = MutableStateFlow<AccessTokenUIState>(AccessTokenUIState.Idle)

    val accessTokenUIState = _accessTokenUIState.asStateFlow()

    fun requestToken(redirectTo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.requestTokenV4(redirectTo)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val response = result.body()
                    requestToken.value = response?.requestToken
                }
            }
        }
    }
    fun createAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            _accessTokenUIState.value = AccessTokenUIState.Waiting
            val result = userRepo.requestAccessTokenV4(requestToken.value!!)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val response = result.body()
                    _accessTokenUIState.value = if (response?.success == true) {
                        AccessTokenUIState.Success(data = response)
                    } else {
                        AccessTokenUIState.Error
                    }
                }
            }
        }
    }
}
interface AccessTokenUIState {
    data class Success(val data: V4RequestAccessTokenResponse): AccessTokenUIState
    data object Waiting : AccessTokenUIState
    data object Error : AccessTokenUIState
    data object Idle : AccessTokenUIState
}