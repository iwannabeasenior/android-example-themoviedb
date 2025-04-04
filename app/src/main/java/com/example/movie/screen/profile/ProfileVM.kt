package com.example.movie.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.base.Result
import com.example.movie.domain.model.UserDetail
import com.example.movie.domain.repo.UserRepo
import com.example.movie.domain.usecase.user.GetUserDetailUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val getUserDetailUC: GetUserDetailUC,
    private val userRepo: UserRepo,
): ViewModel() {

    val userDetailUIState = MutableStateFlow<UserDetailUIState>(UserDetailUIState.Loading)

    fun getUserDetail(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserDetailUC.execute(accountId)
            userDetailUIState.value = when (result) {
                is Result.Success -> {
                    UserDetailUIState.Success(result.data)
                }
                is Result.Error -> {
                    UserDetailUIState.Error
                }
                is Result.Loading -> {
                    UserDetailUIState.Loading
                }
            }
        }
    }

    fun logout(accessToken: String) {
        viewModelScope.launch {
            val response = userRepo.logout(accessToken)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {

                }
            }
        }

    }
}
internal interface LogoutState {

}
interface UserDetailUIState {
    data class Success(val data: UserDetail): UserDetailUIState
    data object Error: UserDetailUIState
    data object Loading: UserDetailUIState
}