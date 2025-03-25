package com.example.movie.screen.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movie.R
import com.example.movie.domain.model.MovieDetail
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.Constant
import com.example.movie.utils.formatTimeDuration

@Composable
fun MovieDetailScreen(movieDetailVM: MovieDetailVM = hiltViewModel()) {

    val movieUiState by movieDetailVM.uiState

    val movie = remember {
        derivedStateOf {
            (movieUiState as? MovieDetailUIState.Success)?.data
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (movieUiState) {
            is MovieDetailUIState.Failure ->
                IconButton(
                    onClick = {
                        movieDetailVM.loadMovieDetail()
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                ) {
                    Icon(Icons.Default.Refresh, "", tint = Color.Black)
                }
            is MovieDetailUIState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is MovieDetailUIState.Success -> {
                SuccessMovieDetail(movie.value!!)
            }
        }

    }
}

@Composable
private fun SuccessMovieDetail(movie: MovieDetail) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                val (image, columnItems) = createRefs()
                AsyncImage(
                    model = Constant.IMAGE_URL + movie.posterPath,
                    "",
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(columnItems.bottom)
                        }
                        .fillMaxWidth(0.5f)
                        .padding(end = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .constrainAs(columnItems) {
                            start.linkTo(image.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        },
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        movie.title ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(GreenMovie),
                            contentAlignment = Alignment.Center // Center content inside the Box
                        ) {
                            Text(
                                "${movie.formatVoteAverage()}%",
                                textAlign = TextAlign.Center,
                                color = Color.Black // Ensure text is visible
                            )
                        }
                        Icon(
                            painter = painterResource(if (true) R.drawable.ic_filled_heart else R.drawable.ic_heart),
                            "",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(PurpleMovie)
                                .padding(10.dp),
                            tint = Color.White
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_save),
                            "",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(PurpleMovie)
                                .padding(10.dp),
                            tint = Color.White
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(Icons.Default.PlayArrow, "")
                        }
                        Text(
                            "Play Trailer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Text(
                        movie.formatGenres(),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )
                    // create chain string
                    Text(movie.formatTime(), fontSize = 16.sp, fontStyle = FontStyle.Italic)
                }
            }
        }

        movie.tagline?.let {
            item {
                Text(it, fontSize = 16.sp)
            }
        }


        movie.overview?.let {

            item {
                Text("Overview", fontSize = 20.sp)
            }

            item {
                Text(it)
            }
        }

        item {
            Column {
                Text("Top Billed Cast", fontSize = 16.sp, fontStyle = FontStyle.Italic)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(10) {
                        ActorItem()
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                    .background(Color.Black)
                    .padding(horizontal = 5.dp, vertical = 10.dp)

            ) {
                Text("Video", fontSize = 16.sp, color = Color.White)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(10) {
                        ItemVideo()
                    }
                }
            }
        }
    }

}
@Preview
@Composable
private fun ItemVideo() {
    Box(
        modifier = Modifier
            .size(height = 60.dp, width = 140.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.img_poster),
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = {
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(Icons.Default.PlayArrow, "", modifier = Modifier.size(30.dp), tint = Color.White)
        }
    }
}

@Preview
@Composable
private fun ActorItem(

) {
    Column(
        modifier = Modifier
            .size(height = 200.dp, width = 100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
//            .shadow(0.5.dp, RoundedCornerShape(10.dp)).padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.img_poster),
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)),
        )
        Text(
            "Halland",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Akashin",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}