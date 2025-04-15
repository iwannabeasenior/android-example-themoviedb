package com.example.model.request

data class DiscoverMoviesParams(
    val sort_by: String = SortBy.popularity.fromOrder(order = Order.desc),
    val vote_count_lte: Float?,
    val vote_count_gte: Float?,
    val with_runtime_gte: Int?,
    val with_runtime_lte: Int?,
    val with_genres: String?,
    val with_keywords: String?
): Params()



