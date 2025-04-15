package com.example.movie.screen.profile.subscreen.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.model.model.Movie
import com.example.movie.R
import com.example.movie.designpattern.smallcomponent.RatingComponent
import com.example.movie.extention.fromRatingToColor
import com.example.movie.extention.toDecimal
import com.example.movie.ui.theme.BlueMovie
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.Pink40
import com.example.movie.ui.theme.Pink80
import com.example.movie.ui.theme.PurpleMovie
import com.example.movie.ui.theme.YellowRating
import com.example.movie.utils.Constant


@Composable
fun MyFavoriteScreen(viewModel: MyFavoriteVM = hiltViewModel(), onMovieClick: (Int) -> Unit) {
    var expanded: Int? by remember {
        mutableStateOf(null)
    }
    val favoriteState by viewModel.favoriteListUIState.collectAsStateWithLifecycle()

    val movies by viewModel.movies.collectAsStateWithLifecycle()

    MyFavoriteScreen(
        favoriteState = favoriteState,
        onRemoveMovieFromList = viewModel::removeMovieFromList,
        expanded = expanded,
        movies = movies,
        onExpandedChange = { expanded = it },
        onDismissRequest = { expanded = null },
        onMovieClick = onMovieClick
    )
}

@Composable
internal fun MyFavoriteScreen(
    favoriteState: FavoriteListUIState,
    onRemoveMovieFromList: (movieType: String, movieId: Int) -> Unit,
    expanded: Int?,
    movies: List<Movie>,
    onExpandedChange: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    when (favoriteState) {
        is FavoriteListUIState.Success -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize().padding(10.dp)) {
                itemsIndexed(items = movies, key = { index, movie -> index }) { index, movie ->
                    FavoriteItemConstraintLayout(
                        movie = movie,
                        index == expanded,
                        onMoreClick = { onExpandedChange(index) },
                        onRemoveMovieFromList = onRemoveMovieFromList,
                        onDismissRequest = onDismissRequest,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
        is FavoriteListUIState.Error -> { }
        is FavoriteListUIState.Loading -> LoadingUI()
    }

}

@Composable
private fun LoadingUI(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
private fun FavoriteItemConstraintLayout(
    movie: Movie,
    expanded: Boolean,
    onRemoveMovieFromList: (movieType: String, movieId: Int) -> Unit,
    onMoreClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onMovieClick: (Int) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(Color.White)
            .clickable {
                onMovieClick.invoke(movie.id)
            }
    ) {
        val (image, rating, title, releaseDate, overview, moreButton) = createRefs()
        AsyncImage(
            model = Constant.IMAGE_URL + movie.posterPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 10.dp)
                .height(150.dp)
                .width(100.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        RatingComponent(
            modifier = Modifier.constrainAs(rating) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(image.end)
            },
            vote = movie.voteAverage,
        )
        Text(
            movie.title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(rating.end)
                    top.linkTo(rating.top)
                }
        )
        Text(
            movie.releaseDate ?: "",
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(releaseDate) {
                    start.linkTo(title.start)
                    bottom.linkTo(rating.bottom)
                }
        )
        Text(
            movie.overview ?: "",
            fontSize = 15.sp,
            color = Color.Black,
            overflow = TextOverflow.Clip,
            softWrap = true,
            maxLines = 4,
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(overview) {
                    start.linkTo(rating.start)
                    top.linkTo(rating.bottom)
                    end.linkTo(moreButton.start)
                    width = Dimension.fillToConstraints
                }
        )
        Column(
            modifier = Modifier.constrainAs(moreButton) {
                end.linkTo(parent.end, margin = 5.dp)
                start.linkTo(overview.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                verticalChainWeight = 0.5f
            }

        ) {

            IconButton(
                onClick = {
                    onMoreClick()
                },
                modifier = Modifier
            ) {
                Icon(Icons.Default.MoreVert, null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
            ) {
                RatingSubItem(movie.rating ?: 0)
                FavoriteSubItem()
                RemoveItem(movie = movie, onRemoveMovieFromList = onRemoveMovieFromList)
            }
        }

    }
}


@Composable
fun RatingSubItem(rating: Int) {
    DropdownMenuItem(
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BlueMovie)
            ) {
                Text(
                    rating.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        text = {
            Text("Your rating")
        },
        onClick = {
        }
    )
}

@Composable
fun FavoriteSubItem() {
    DropdownMenuItem(
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Pink80)
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Default.Favorite,
                    null,
                    tint = Color.White,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        },
        text = {
            Text("Favorite")
        },
        onClick = {
        }
    )
}

@Composable
fun RemoveItem(
    movie: Movie,
    onRemoveMovieFromList: (movieType: String, movieId: Int) -> Unit
) {
    DropdownMenuItem(
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = 0.5.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Close,
                    null,
                    tint = Color.Gray,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        },
        text = {
            Text("Remove")
        },
        onClick = {
            onRemoveMovieFromList(movie.mediaType ?: MOVIE_TYPE, movie.id)
        }
    )
}

private const val MOVIE_TYPE = "movie"