package com.example.movie.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movie.R
import com.example.movie.ui.theme.BlueMovie
import com.example.movie.ui.theme.GrayMovie
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.HintTextMovie
import com.example.movie.ui.theme.PinkMovie
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.ui.theme.YellowMovie
import com.example.movie.utils.Constant
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(FlowPreview::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), navigateToMovieDetail: (Int) -> Unit) {
    // handle text search when configuration change
    var query by remember {
        mutableStateOf("")
    }

    val trendingMovieState by homeViewModel.trendingUiState
    val trendingWeekUiState by homeViewModel.trendingWeekUiState
    val queryResult by homeViewModel.queryResult.collectAsStateWithLifecycle()

    var currentTrendingMode by remember {
        mutableStateOf(TrendingMode.day)
    }

    var canSearch = remember {
        derivedStateOf { query != "" }
    }
    LaunchedEffect(true) {
        // state -> flow -> collect -> search
//        snapshotFlow { query }
//            .distinctUntilChanged()
//            .debounce(300)
//            .collect {
//                if (it.isNotEmpty()) {
//                    homeViewModel.searchByKeyword(it)
//                }
//            }
    }
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchSection(query = query, canSearch = canSearch.value) {
                query = it
            }

            // Trending
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinkMovie)
                    .padding(horizontal = 10.dp, vertical = 30.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text("Trending", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(PurpleMovie)
                    ) {

                        ModeTrendingItem(
                            currentTrendingMode = currentTrendingMode,
                            title = "Today",
                            itemTrendingMode = TrendingMode.day
                        ) { trendingMode ->
                            currentTrendingMode = trendingMode
                        }
                        ModeTrendingItem(
                            currentTrendingMode = currentTrendingMode,
                            title = "This Week",
                            itemTrendingMode = TrendingMode.week
                        ) { trendingMode ->
                            currentTrendingMode = trendingMode
                        }
                    }
                }
                if (currentTrendingMode == TrendingMode.day) {
                    TrendingSection(
                        trendingMovieState = trendingMovieState,
                        navigateToMovieDetail = navigateToMovieDetail
                    )
                } else {
                    TrendingWeekSection(
                        trendingWeekUiState = trendingWeekUiState,
                        navigateToMovieDetail = navigateToMovieDetail
                    )
                }

                // Lastest trailer

                LastestTrailer(currentTrendingMode = currentTrendingMode)

            }
        }
    }
}


@Composable
fun TrendingSection(trendingMovieState: TrendingUiState, navigateToMovieDetail: (Int) -> Unit) {
    when (trendingMovieState) {
        is TrendingUiState.Loading -> CircularProgressIndicator()
        is TrendingUiState.Error -> CircularProgressIndicator()
        is TrendingUiState.Success ->
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items((trendingMovieState).movies) { item ->
                    TrendingItem(
                        item.id,
                        item.posterPath,
                        item.title,
                        item.releaseDate
                    ) { id ->
                        navigateToMovieDetail(id)
                    }
                }
            }
    }
}
@Composable
fun TrendingWeekSection(trendingWeekUiState: TrendingWeekUiState, navigateToMovieDetail: (Int) -> Unit) {
    when (trendingWeekUiState) {
        is TrendingWeekUiState.Loading -> CircularProgressIndicator()
        is TrendingWeekUiState.Error -> CircularProgressIndicator()
        is TrendingWeekUiState.Success ->
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(trendingWeekUiState.movies) { item ->
                    TrendingItem(
                        item.id,
                        item.posterPath,
                        item.title,
                        item.releaseDate
                    ) { id ->
                        navigateToMovieDetail(id)
                    }
                }
            }
    }

}

@Composable
fun LastestTrailer(currentTrendingMode: TrendingMode) {
    Box {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    "Latest trailer",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(GrayMovie)
                ) {
                    Text(
                        "Popular",
                        fontSize = 10.sp,
                        color = if (currentTrendingMode == TrendingMode.week) Color.Black else Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (currentTrendingMode == TrendingMode.week) GreenMovie else GrayMovie)
                            .padding(5.dp)
                    )
                    Text(
                        "In theater",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (currentTrendingMode == TrendingMode.week) GreenMovie else GrayMovie)
                            .padding(5.dp),
                        color = if (currentTrendingMode == TrendingMode.week) Color.Black else Color.White
                    )
                }
            }

            Spacer(Modifier.height(15.dp))
        }
    }

}
@Composable
fun SearchSection(query: String, canSearch: Boolean, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_poster),
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "Welcome",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                "Millions of movies, TV shows and people to discover",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )


            TextField(
                value = query,
                onValueChange = {
                    onValueChange(it)
                    // debounce result search

                },
                placeholder = {
                    Text(
                        text = "Search movie you want...",
                        fontSize = 14.sp,
                        color = HintTextMovie,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                trailingIcon = {
                    Text(
                        "Search",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .height(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable(
                                enabled = canSearch
                            ) {

                            }
                            .background(
                                if (canSearch) BlueMovie else BlueMovie.copy(
                                    alpha = 0.7f
                                )
                            )
                            .padding(horizontal = 20.dp)
                            .wrapContentSize(Alignment.Center),
                    )
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
            )
        }
    }

}

@Composable
private fun TrendingItem(
    id: Int,
    url: String?,
    title: String?,
    releaseDate: String?,
    onNavigate: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(70.dp)
            .clickable {
                onNavigate(id)
            }
    ) {

        AsyncImage(
            model = "${Constant.IMAGE_URL}${url}",
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Fit
        )
        Text(
            title ?: "",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            releaseDate ?: "",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TrailerItem() {
    Box(

    ) {
        Image(
            painter = painterResource(R.drawable.img_poster),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(height = 60.dp, width = 140.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Icon(Icons.Default.PlayArrow, "", Modifier
            .size(30.dp)
            .align(Alignment.Center), tint = Color.White,)
    }
}

@Composable
fun ModeTrendingItem(currentTrendingMode: TrendingMode, title: String, itemTrendingMode: TrendingMode, onChangeTrendingMode: (TrendingMode) -> Unit) {
    Text(
        title,
        fontSize = 10.sp,
        color = if (currentTrendingMode == itemTrendingMode) Color.Black else Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (currentTrendingMode == itemTrendingMode) YellowMovie else PurpleMovie)
            .clickable {
                if (currentTrendingMode != itemTrendingMode) {
                    onChangeTrendingMode(itemTrendingMode)
                }
            }
            .padding(5.dp)
    )
}
enum class TrendingMode {
    day,
    week
}

enum class TrailerMode {
    popular,

}