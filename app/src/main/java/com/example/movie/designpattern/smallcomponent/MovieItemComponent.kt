package com.example.movie.designpattern.smallcomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.model.Movie
import com.example.movie.R
import com.example.movie.ui.theme.GrayMovie
import com.example.movie.utils.Constant

@Composable
fun MovieItemComponent(movie: Movie) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(10))
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
        ) {
            AsyncImage(model = Constant.IMAGE_URL + movie.posterPath, modifier = Modifier.fillMaxWidth().height(220.dp), contentDescription = null)
            RatingComponent(
                modifier = Modifier.align(Alignment.BottomStart).offset(x = 20.dp, y = 20.dp),
                vote = movie.voteAverage,
            )
        }

        Spacer(Modifier.height(15.dp))

        Text(movie.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)

        Text(movie.releaseDate ?: "", fontSize = 20.sp, color = GrayMovie, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

