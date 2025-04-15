package com.example.movie.extention

import androidx.compose.ui.graphics.Color
import com.example.movie.ui.theme.BlueMovie
import com.example.movie.ui.theme.GrayMovie
import com.example.movie.ui.theme.GreenMovie
import com.example.movie.ui.theme.YellowRating

fun Float?.fromRatingToColor(): Color {
    if (this == null) return GrayMovie
    return when(this) {
        in 0f..4f -> Color.Red
        in 4f..7f -> YellowRating
        in 7f..10f -> GreenMovie
        else -> GrayMovie
    }
}