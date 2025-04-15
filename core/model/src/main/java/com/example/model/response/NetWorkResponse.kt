package com.example.model.response

import com.google.gson.annotations.SerializedName

data class NetWorkResponse<T> (
    @SerializedName("results") val results: T,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)