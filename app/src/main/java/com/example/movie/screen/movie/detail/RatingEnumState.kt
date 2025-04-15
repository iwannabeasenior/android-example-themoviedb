package com.example.movie.screen.movie.detail

enum class RatingEnumState(val rating: Int, val title: String) {
    ONE(1, "Absolute Trash"),
    TWO(2, "Garbage"),
    THREE(3, "Truly Bad"),
    FOUR(4, "Not Good"),
    FIVE(5, "Passable"),
    SIX(6, "It's Alright"),
    SEVEN(7, "Pretty Decent"),
    EIGHT(8, "Really Good"),
    NINE(9, "Greatness"),
    TEN(10, "Champion");

    companion object {
        fun fromScore(rating: Int): String? {
            return entries.find { it.rating == rating }?.title
        }
    }
}