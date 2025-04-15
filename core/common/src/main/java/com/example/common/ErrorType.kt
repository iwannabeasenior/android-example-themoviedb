package com.example.common

sealed class ErrorType {
    object NetWorkError : ErrorType()
    object Unauthorized : ErrorType()
    object HttpException : ErrorType()
    data class UnknownError(val throwable: Throwable) : ErrorType()
}