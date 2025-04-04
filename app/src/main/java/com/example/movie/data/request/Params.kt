package com.example.movie.data.request

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import kotlin.reflect.full.memberProperties

abstract class Params {
    fun toRawJson(): String {
        var rawJson = """"""

        val listOfPairPropValue = this::class.memberProperties.map { prop ->
            prop.name to prop.getter.call(this)
        }

        listOfPairPropValue.forEachIndexed { index, pair ->
            rawJson = if (index != listOfPairPropValue.size - 1) {
                rawJson + """"${pair.first}"""" + """:""" + """"${pair.second}"""" + ""","""
            } else {
                rawJson + """"${pair.first}"""" + """:""" + """"${pair.second}""""
            }
        }
        return rawJson
    }
    fun createRawRequestBody(): RequestBody {
        val rawJson = this.toRawJson()
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),
            """{${rawJson}}"""
        )
        return requestBody
    }
}