package com.example.movie.screen.movie.list

import com.example.model.model.Genre

object MovieCacheInstance {
    var genres: List<Genre> = emptyList()
        private set

    fun updateGenres(newData: List<Genre>) {
        genres = newData
    }

}