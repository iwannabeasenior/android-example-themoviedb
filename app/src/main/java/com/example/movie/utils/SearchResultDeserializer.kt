package com.example.movie.utils

import com.example.model.response.MovieSearchResult
import com.example.model.response.PersonSearchResult
import com.example.model.response.SearchResult
import com.example.model.response.TVSearchResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class SearchResultDeserializer : JsonDeserializer<List<SearchResult>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<SearchResult>? {
        val resultArray = json?.asJsonArray
        return resultArray
            ?.map { it ->
                val mediaType = it.asJsonObject.get("media_type")?.asString
                return when(mediaType) {
                    "person" -> context?.deserialize(it, PersonSearchResult::class.java)
                    "movie" -> context?.deserialize(it, MovieSearchResult::class.java)
                    "tv" -> context?.deserialize(it, TVSearchResult::class.java)
                    else -> throw JsonParseException("Unknown media type: $mediaType")
                }
            }
    }
}