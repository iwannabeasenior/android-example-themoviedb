package com.example.movie.extention


fun Double?.toDecimal(): Int {
    return (this?.times(10))?.toInt() ?: 0
}