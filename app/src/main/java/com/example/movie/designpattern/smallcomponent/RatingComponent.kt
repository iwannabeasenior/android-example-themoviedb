package com.example.movie.designpattern.smallcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movie.extention.fromRatingToColor
import com.example.movie.extention.toDecimal
import com.example.movie.ui.theme.PurpleMovie

@Composable
fun RatingComponent(modifier: Modifier = Modifier, vote: Double?) {
    val color = remember {
        vote?.toFloat().fromRatingToColor()
    }
    Box(
        modifier = Modifier
            .then(modifier)
            .padding(end = 10.dp)
            .size(50.dp)
            .clip(CircleShape)
            .background(PurpleMovie)
            .drawBehind {
                drawArc(
                    color = color.copy(alpha = 0.5f),
                    startAngle = -90f,
                    sweepAngle = Float.POSITIVE_INFINITY,
                    useCenter = false,
                    style = Stroke(width = 20f)
                )
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f * 0.34f,
                    useCenter = false,
                    style = Stroke(width = 20f)
                )
            }
    ) {
        Text(
            vote.toDecimal().toString(),
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
