package com.example.movie.screen.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movie.R
import com.example.movie.domain.model.CastMember
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.model.Video
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.Constant
import com.example.movie.utils.YoutubeUtil
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.jetbrains.annotations.Async

@Composable
fun MovieDetailScreen(movieDetailVM: MovieDetailVM = hiltViewModel()) {

    val movieUiState by movieDetailVM.uiState

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            enabled = movieDetailVM.showVideo.value
        ) {
            movieDetailVM.showVideo(false)
        }
    ) {
        when (movieUiState) {
             is MovieDetailUIState.Failure ->
                FailureComponent(
                    Modifier.align(Alignment.Center)
                ) {
                    movieDetailVM.loadMovieDetail()
                }
            is MovieDetailUIState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is MovieDetailUIState.Success -> {
                SuccessMovieDetail((movieUiState as MovieDetailUIState.Success).data)
            }
        }
        if (movieDetailVM.showVideo.value && movieDetailVM.currentVideoKey.value != null) {
            YouTubePlayerScreen(modifier = Modifier.align(Alignment.Center),
                movieDetailVM.currentVideoKey.value!!
            )
        }
    }
}

@Composable
fun FailureComponent(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(60.dp)
            .then(modifier)
    ) {
        Icon(Icons.Default.Refresh, "", tint = Color.Black)
    }
}
@Composable
private fun SuccessMovieDetail(movie: MovieDetail) {
    
    val vm: MovieDetailVM = hiltViewModel()
    
    val videoUIState by vm.videoUiState

    val castUIState by vm.castUiState

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
                    null,
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
                        }
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        movie.title ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                when (castUIState) {
                    is CastUIState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items((castUIState as CastUIState.Success).data) {
                                ActorItem(cast = it) {
                                    // navigate to actor detail page
                                }
                            }
                        }
                    }
                    is CastUIState.Failure -> FailureComponent { vm.loadCasts() }
                    is CastUIState.Loading -> CircularProgressIndicator()
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
                when (videoUIState) {
                    is VideoUIState.Failure -> FailureComponent {
                        vm.loadVideos()
                    }
                    is VideoUIState.Loading -> CircularProgressIndicator()
                    is VideoUIState.Success -> {
                        Text("Video", fontSize = 16.sp, color = Color.White)
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items ((videoUIState as VideoUIState.Success).data) {
                                ItemVideo(it) {
                                    vm.changeCurrentKey(it.key)
                                    vm.showVideo(true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemVideo(video: Video, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(height = 60.dp, width = 140.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        video.key?.let {
            AsyncImage(
                model = YoutubeUtil.createYoutubeThumbnailURL(it),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        IconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(Icons.Default.PlayArrow, "", modifier = Modifier.size(30.dp), tint = Color.White)
        }
    }
}
@Composable
fun YouTubePlayerScreen(modifier: Modifier = Modifier, videoKey: String) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifeCycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoKey, 0f)
                    }
                })
            }
        },
        modifier = Modifier.fillMaxWidth().height(250.dp).then(modifier)
    )
}


@Composable
private fun ActorItem(
    cast: CastMember,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(height = 200.dp, width = 100.dp)
            .clickable {
                onClick()
            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        AsyncImage(
            model = Constant.IMAGE_URL + cast.profilePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
        )
        Text(
            cast.originalName,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            cast.character,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}