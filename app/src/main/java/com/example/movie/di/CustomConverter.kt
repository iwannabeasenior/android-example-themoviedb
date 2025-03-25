package com.example.movie.di

import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ValueExtractorConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return Converter { responseBody ->
            val responseString = responseBody.string()

            try {
                val jsonObject = JSONObject(responseString)
                val valueObject = jsonObject.optJSONObject("result")

                if (valueObject != null) {
                    gson.fromJson(valueObject.toString(), type)
                } else {
                    gson.fromJson(responseString, type) // Fallback if "value" doesn't exist
                }
            } catch (e: JSONException) {
                throw JsonParseException("Error parsing response")
            }
        }
    }
}
