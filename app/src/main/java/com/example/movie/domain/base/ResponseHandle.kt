package com.example.movie.domain.base

import retrofit2.Response

fun<T, K> handleResponse(response: Response<T>, map: (T) -> K) : Result<K>{
    return try {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(map(body))
            } else {
                Result.Error("Response body is null")
            }
        } else {
            Result.Error("Error ${response.code()}: ${response.message()}")
        }
    } catch(e: Exception) {
        Result.Error("Unexpected error: ${e.localizedMessage}")
    }
}

fun <T> handleResponse(response: Response<T>): Result<T> {
    return try {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error("Response body is null")
            }
        } else {
            Result.Error("Error ${response.code()}: ${response.message()}")
        }
    } catch (e: Exception) {
        Result.Error("Unexpected error: ${e.message}")
    }
}