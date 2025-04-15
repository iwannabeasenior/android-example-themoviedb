package com.example.model.request

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import kotlin.reflect.full.memberProperties

abstract class Params {
    private fun toRawJson(): String {
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

    fun toMap(): Map<String, Any?> {
        val maps = this::class.memberProperties
            .toList()
            .filter { prop -> prop.getter.call(this) != null }
            .associate { prop -> formatNameToMap(prop.name) to prop.getter.call(this) }

        return maps
    }
    fun formatNameToMap(name: String): String {
        val size = name.length
        val endString = name.substring(size - 3, size)
        return if (endString == "lte" || endString == "gte") {
            name.substring(0, size - 4) + "." + endString
        } else {
            name
        }
    }
}