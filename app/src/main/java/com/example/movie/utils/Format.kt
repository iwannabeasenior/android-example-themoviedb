package com.example.movie.utils


fun formatTimeDuration(time: Int) : String {
    val hour = (time / 60).run {
        if (this < 10) "0$this"
        else this.toString()
    }
    val minute = (time % 60).run {
        if (this < 10) "0$this"
        else this.toString()
    }
    return "${hour}h ${minute}m"
}