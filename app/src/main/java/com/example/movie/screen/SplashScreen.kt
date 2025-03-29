package com.example.movie.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import com.example.movie.R
import com.example.movie.ui.theme.BlueMovie
import com.example.movie.ui.theme.PurpleMovie


@Preview
@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    val splashImage = listOf<Int>(
        R.drawable.gbu,
        R.drawable.forrest_gump,
        R.drawable.the_dark_knight
    )
    val splashTitle = listOf<Int>(
        R.string.splash_movie_1,
        R.string.splash_movie_2,
        R.string.splash_movie_3
    )
    val releaseDate = listOf<String>(
        "December 23, 1966",
        "July 6, 1994",
        "July 18, 2008"
    )
    var currentIndex by remember {
        mutableIntStateOf(0)
    }

    var maxIndex = splashImage.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Skip", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        Text(stringResource(R.string.app_title), modifier = Modifier, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BlueMovie)

        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)).togetherWith(fadeOut(animationSpec = tween(500)))
            }
        ) { index ->
            Image(
                painter = painterResource(splashImage[index]),
                "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .aspectRatio(0.6f)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }

        Text(stringResource(splashTitle[currentIndex]), fontSize = 25.sp, fontWeight = FontWeight.Bold)

        Text(releaseDate[currentIndex], fontSize = 25.sp, fontWeight = FontWeight.Bold)

        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                currentIndex = currentIndex,
                maxIndex = maxIndex
            )
            IconButton(
                onClick = {
                    if (currentIndex == maxIndex) {
                        onNavigateToLogin()
                    } else {
                        currentIndex++
                    }
                },
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            ) {
                Icon(Icons.Default.KeyboardArrowRight, "", tint = Color.White)
            }
        }
    }
}
@Composable
private fun Slider(currentIndex: Int, maxIndex: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in 0..maxIndex) {
            AnimatedContent(
                targetState = (i == currentIndex), // Animate only when the index matches
                transitionSpec = {
                    fadeIn(animationSpec = tween(1000)) togetherWith
                            fadeOut(animationSpec = tween(1000))
                }
            ) { isSelected ->
                if (isSelected) LongSlider() else ShortSlider()
            }
        }
    }
}

@Preview
@Composable
private fun ShortSlider() {
    Box(
        modifier = Modifier
            .size(24.dp, 12.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(BlueMovie)
    )
}

@Preview
@Composable
private fun LongSlider() {
    Box(
        modifier = Modifier
            .size(50.dp, 12.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(PurpleMovie)
    )
}


