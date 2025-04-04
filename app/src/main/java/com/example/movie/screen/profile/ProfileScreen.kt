package com.example.movie.screen.profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.ripple.createRippleModifierNode
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.DialogHost
import coil.compose.AsyncImage
import com.example.movie.R
import com.example.movie.domain.model.UserDetail
import com.example.movie.ui.MovieAppState
import com.example.movie.ui.theme.GrayMovie
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.log

// always learning how it works

@Composable
fun ProfileScreen(vm: ProfileVM = hiltViewModel(), appState: MovieAppState) {

    var openDialog by remember {
        mutableStateOf(false)
    }
    val userDetailUIState = vm.userDetailUIState.collectAsStateWithLifecycle()

    fun removeUserData() {
        appState.coroutineScope.launch {
            appState.userPreferences.apply {
                removeAccessToken()
                removeAccountId()
            }
        }
    }

    fun logOut() {
        try {
            vm.logout(appState.userPreferences.accessToken!!)
            removeUserData()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
    LaunchedEffect(Unit) {
        val accountId = appState.userPreferences.getAccountId().filterNotNull().distinctUntilChanged().first()
        vm.getUserDetail(accountId = accountId)
    }

    if (openDialog) {
        LogoutDialog(
            onDismissRequest = { openDialog = false },
            onConfirmation = {
                openDialog = false
                logOut()
            },
            dialogTitle = "Do you wanna logout?",
            icon = Icons.Default.ExitToApp
        )
    }

    ProfileScreen(userDetailUIState.value, onLogoutClick = { openDialog = true })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = null, Modifier.size(30.dp))
        },
        title = {
            Text(dialogTitle, fontSize = 15.sp)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = { 
            TextButton(onClick = { onConfirmation() }) {
                Text("OK", fontSize = 15.sp)
            } 
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) { 
                Text("Dismiss", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        },
    )
}
@Composable
internal fun ProfileScreen(
    userDetailUIState: UserDetailUIState,
    onLogoutClick: () -> Unit,
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 25.dp)
    ) {
        when(userDetailUIState) {
            is UserDetailUIState.Loading -> {
                CircularProgressIndicator()
            }
            is UserDetailUIState.Success -> {
                InfoSection(userDetailUIState.data)
            }
            is UserDetailUIState.Error -> {

            }
        }


        ListItemSection(
            onNavigateToLists = {},
            onNavigateFavorite = {},
            onNavigateWatchList = {},
            onNavigateRating = {},
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = GrayMovie
            )

            Spacer(Modifier.height(15.dp))

            LogoutButton(onLogoutClick)
        }
    }
}

@Composable
private fun ListItemSection(
    onNavigateToLists: () -> Unit,
    onNavigateFavorite: () -> Unit,
    onNavigateWatchList: () -> Unit,
    onNavigateRating: () -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Item(
            icon = R.drawable.ic_lists,
            title = "My Lists"
        ) {
            onNavigateToLists()
        }
        Item(
            icon = R.drawable.ic_favourite,
            title = "My favorite"
        ) {
            onNavigateFavorite()
        }
        Item(
            icon = R.drawable.ic_watch_list,
            title = "My watchlist"
        ) {
            onNavigateWatchList()
        }
        Item(
            icon = R.drawable.ic_rated,
            title = "My rated"
        ) {
            onNavigateRating()
        }

    }
}

@Composable
private fun Item(icon: Int, title: String, onClick: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                indication = ripple(color = PurpleMovie),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .padding(start = 30.dp)
    ) {
        Image(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(30.dp))
        Text(title, fontSize = 20.sp)
    }
}

@Composable
private fun LogoutButton(onLogoutClick: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onLogoutClick()
        }
    ) {
        Icon(Icons.Default.ExitToApp, contentDescription = null, tint = GrayMovie, modifier = Modifier.size(50.dp))
        Text("Logout", fontSize = 20.sp, color = GrayMovie)
    }
}


@Composable
fun InfoSection(info: UserDetail) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = Constant.IMAGE_URL + info.avatar?.tmdb?.avatar_path,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(20.dp))

        Text(info.username, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}