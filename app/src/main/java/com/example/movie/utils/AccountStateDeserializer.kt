package com.example.movie.utils

import com.example.model.response.MovieAccountStateResponse
import com.example.model.response.RatedMovieAccountStateResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AccountStateDeserializer : JsonDeserializer<MovieAccountStateResponse?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MovieAccountStateResponse? {
        val jsonObject = json?.asJsonObject

        val ratedObject = jsonObject?.get(RATED_OBJECT_NAME)
        val idObject = jsonObject?.get(ID_OBJECT_NAME)
        val favoriteObject = jsonObject?.get(FAVORITE_OBJECT_NAME)
        val watchlistObject = jsonObject?.get(WATCH_LIST_OBJECT_NAME)

        val id = context?.deserialize<Int>(idObject, Int::class.java)
        val favorite = context?.deserialize<Boolean>(favoriteObject, Boolean::class.java)
        val watchlist = context?.deserialize<Boolean>(watchlistObject, Boolean::class.java)

        return if (ratedObject?.isJsonObject == true) {
            val rated = context?.deserialize<RatedMovieAccountStateResponse>(ratedObject, RatedMovieAccountStateResponse::class.java)
            MovieAccountStateResponse(
                id = id!!,
                favorite = favorite,
                rated = rated,
                watchlist = watchlist
            )
        } else {
            MovieAccountStateResponse(
                id = id!!,
                favorite = favorite,
                rated = null,
                watchlist = watchlist
            )
        }
    }
    companion object {
        const val RATED_OBJECT_NAME = "rated"
        const val ID_OBJECT_NAME = "id"
        const val FAVORITE_OBJECT_NAME = "favorite"
        const val WATCH_LIST_OBJECT_NAME = "watchlist"
    }
}