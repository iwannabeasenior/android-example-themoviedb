package com.example.model.request

enum class SortBy {
    original_title,
    popularity,
    revenue,
    primary_release_data,
    vote_average,
    vote_count,
    title;

    fun fromOrder(order: Order): String = this.name + "." + order.name
}

enum class Order {
    asc, desc
}
