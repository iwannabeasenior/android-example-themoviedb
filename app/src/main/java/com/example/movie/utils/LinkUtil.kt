package com.example.movie.utils

import timber.log.Timber


object LinkUtil {
    fun createYoutubeThumbnailURL(key: String): String {
        return "https://img.youtube.com/vi/${key}/hqdefault.jpg"
    }
    fun createUserPermissionURL(requestToken: String): String {
        val url = "https://www.themoviedb.org/authenticate/${requestToken}?redirect_to=mymovie://auth/callback"
        return url
    }
}