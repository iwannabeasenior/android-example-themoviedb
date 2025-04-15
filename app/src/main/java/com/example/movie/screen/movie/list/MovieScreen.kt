package com.example.movie.screen.movie.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie.base.DefaultScreenUI
import com.example.common.UIComponent
import com.example.common.UIComponent.DialogSimple
import com.example.movie.ui.MovieAppState
import com.example.movie.ui.theme.BlueMovie


@Composable
fun MovieScreen(appState: MovieAppState, vm: MovieVM = hiltViewModel()) {
    // define list of state
    DefaultScreenUI(uiState = appState, errors = vm.errors) {
        MovieScreen()
    }
}

@Composable
internal fun MovieScreen() {
    LazyVerticalGrid(columns = GridCells.Adaptive(50.dp)) {

    }
}

@Preview
@Composable
private fun FilterSection() {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp)) {
        // create list here
        items(genres) { item ->
            GenreItem(title = item, onItemClick = {})
        }
    }
}

@Composable
private fun GenreItem(title: String, onItemClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable { onItemClick(title) }
            .background(BlueMovie)
            .padding(5.dp)
    ) {
        Text(
            title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}



val genres = listOf<String>(
    "Hello",
    "Hello Kitty",
    "Naruto",
    "Doraemon",
    "What are you doing ?",
    "So much thing todo"
)