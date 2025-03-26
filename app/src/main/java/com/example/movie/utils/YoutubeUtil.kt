package com.example.movie.utils


object YoutubeUtil {
    fun createYoutubeThumbnailURL(key: String): String {
        return "https://img.youtube.com/vi/${key}/hqdefault.jpg"
    }
}