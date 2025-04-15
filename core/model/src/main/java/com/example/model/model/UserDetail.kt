package com.example.model.model

data class UserDetail(
    val avatar: Avatar?,
    val id: Int,
    val name: String,
    val username: String
)

data class Avatar(
    val tmdb: Tmdb
)

data class Tmdb(
    val avatar_path: String?
)
