package com.example.movie.domain.base

sealed class Result<out T>{
    object Loading: Result<Nothing>()
    data class Success<T>(val data: T): Result<T>() // cannot modify data
    data class Error(val message: String): Result<Nothing>()
}