package com.example.movie.screen.movie.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.model.model.CastMember
import com.example.model.model.MovieAccountState
import com.example.model.model.MovieDetail
import com.example.model.model.Video
import com.example.movie.R
import com.example.movie.extention.fromRatingToColor
import com.example.movie.ui.theme.BlueMovie
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.utils.Constant
import com.example.movie.utils.LinkUtil
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun MovieDetailScreen(movieDetailVM: MovieDetailVM = hiltViewModel(), navigateToPersonDetail: (Int) -> Unit) {

    val movieUiState by movieDetailVM.uiState

    val accountState by movieDetailVM.movieAccountState.collectAsStateWithLifecycle()

    MovieDetailScreen(
        movieDetailVM = movieDetailVM,
        movieUiState = movieUiState,
        accountState = accountState,
        navigateToPersonDetail = navigateToPersonDetail
    )
}
@Composable
internal fun MovieDetailScreen(
    movieDetailVM: MovieDetailVM,
    movieUiState: MovieDetailUIState,
    accountState: MovieAccountState?,
    navigateToPersonDetail: (Int) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            enabled = movieDetailVM.showVideo.value
        ) {
            movieDetailVM.showVideo(false)
        }
    ) {
        when (movieUiState) {
            is MovieDetailUIState.Error ->
                FailureComponent(
                    Modifier.align(Alignment.Center)
                ) {
                    movieDetailVM.loadMovieDetail()
                }
            is MovieDetailUIState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is MovieDetailUIState.Success -> {
                SuccessMovieDetail(
                    vm = movieDetailVM,
                    movie = movieUiState.data,
                    accountState = accountState,
                    navigateToPersonDetail = navigateToPersonDetail
                )
            }
        }

        if (movieDetailVM.showVideo.value && movieDetailVM.currentVideoKey.value != null) {
            YouTubePlayerScreen(modifier = Modifier.align(Alignment.Center),
                movieDetailVM.currentVideoKey.value!!
            )
        }

        if (movieDetailVM.showRatingPicker.value) {
            RatingPicker(
                rating = accountState?.rated,
                title = "Movie",
                onDismiss = {
                    movieDetailVM.showRatingPicker.value = false
                },
                onActionRating = {
                    movieDetailVM.onActionRating(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RatingPicker(rating: Int?, title: String, onActionRating: (Int) -> Unit, onDismiss: () -> Unit) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    
    var rating by remember {
        mutableFloatStateOf(rating?.toFloat() ?: 0f)
    }

    var color = remember {
        derivedStateOf {
            rating.fromRatingToColor()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "What did you think of This Movie?",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Black
            )

            if (rating != 0f) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 20.sp)
                        ) {
                            append("Rating: ")
                        }
                        withStyle(
                            SpanStyle(fontWeight = FontWeight.Bold, color = color.value, fontSize = 20.sp)
                        ) {
                            append("${rating.toInt()} - ${RatingEnumState.fromScore(rating.toInt())}")
                        }
                    }
                )
            }
            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = color.value,
                    activeTrackColor = color.value,
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            TextButton(
                onClick = {
                    onActionRating(rating.toInt())
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.3f)
                    .clip(RoundedCornerShape(60))
                    .background(PurpleMovie)
                    .padding(5.dp)
            ) {
                Text("SAVE", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
            }
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
        Icon(Icons.Default.Refresh, null, tint = Color.Black)
    }
}
@Composable
private fun SuccessMovieDetail(vm: MovieDetailVM, movie: MovieDetail, accountState: MovieAccountState?,  navigateToPersonDetail: (Int) -> Unit) {
    
    val videoUIState by vm.videoUiState

    val castUIState by vm.castUiState

    var color = accountState?.rated?.toFloat().fromRatingToColor()

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
                    RatingButton(
                        accountState = accountState,
                        vm = vm,
                        color = color
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VoteAverage(movie = movie)
                        FavoriteButton(
                            accountState = accountState,
                            movie = movie,
                            vm = vm
                        )
                        WatchlistButton(
                            accountState = accountState,
                            vm = vm,
                            movie = movie
                        )
                    }
                    PlayTrailer()
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
        // tagline
        movie.tagline?.let {
            item {
                Text(it, fontSize = 16.sp)
            }
        }

        //overview
        movie.overview?.let {

            item {
                Text("Overview", fontSize = 20.sp)
            }

            item {
                Text(it)
            }
        }
        //top billed cast
        item {
            Column {
                Text("Top Billed Cast", fontSize = 16.sp, fontStyle = FontStyle.Italic)
                when (castUIState) {
                    is CastUIState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items((castUIState as CastUIState.Success).data) {
                                ActorItem(cast = it, navigateToPersonDetail)
                            }
                        }
                    }
                    is CastUIState.Error -> FailureComponent { vm.loadCasts() }
                    is CastUIState.Loading -> CircularProgressIndicator()
                }
            }
        }
        //video
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                    .background(Color.Black)
                    .padding(horizontal = 5.dp, vertical = 10.dp)

            ) {
                when (videoUIState) {
                    is VideoUIState.Error -> FailureComponent {
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
                model = LinkUtil.createYoutubeThumbnailURL(it),
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
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .then(modifier)
    )
}


@Composable
private fun ActorItem(
    cast: CastMember,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .size(height = 200.dp, width = 100.dp)
            .clickable {
                onClick(cast.id)
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
        Text(cast.originalName ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            cast.character ?: "",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun VoteAverage(movie: MovieDetail) {
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

}

@Composable
fun FavoriteButton(accountState: MovieAccountState?, movie: MovieDetail, vm: MovieDetailVM) {
    IconButton(
        onClick = {
            accountState?.favorite?.let {
                vm.addToFavorite(
                    mediaType = "movie",
                    mediaId = movie.id,
                    favorite = !it
                )
            }
        }
    ) {
        Icon(
            painter = painterResource(if (accountState?.favorite == true) R.drawable.ic_filled_heart else R.drawable.ic_heart),
            null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(PurpleMovie)
                .padding(10.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun WatchlistButton(accountState: MovieAccountState?, vm: MovieDetailVM, movie: MovieDetail) {
    IconButton(
        onClick = {
            accountState?.watchlist?.let {
                vm.addToWatchlist(
                    mediaType = "movie",
                    mediaId = movie.id,
                    watchlist = !it
                )
            }
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_save),
            "",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(PurpleMovie)
                .padding(10.dp),
            tint = if (accountState?.watchlist == true) GreenMovie else Color.White
        )
    }

}

@Composable
fun PlayTrailer(modifier: Modifier = Modifier) {
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
}

@Composable
fun RatingButton(accountState: MovieAccountState?, vm: MovieDetailVM, color: Color) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clickable {
                vm.showRatingPicker.value = true
            }
            .clip(RoundedCornerShape(60))
            .background(BlueMovie)
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Text(
            text = if (accountState?.rated == null) "What's your Vibe?" else "Your Vibe",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        if (accountState?.rated != null) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = color, fontSize = 15.sp)){
                        append("${accountState.rated!! * 10}")
                    }
                    withStyle(style = SpanStyle(color = Color.White, fontSize = 10.sp)){
                        append("%")
                    }
                }
            )
        } else {
            Icon(painter = painterResource(R.drawable.ic_question_mark), null, modifier = Modifier.size(20.dp))
        }

    }
}