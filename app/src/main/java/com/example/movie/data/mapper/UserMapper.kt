package com.example.movie.data.mapper

import com.example.movie.data.response.AvatarResponse
import com.example.movie.data.response.MovieAccountStateResponse
import com.example.movie.data.response.UserDetailResponse
import com.example.movie.domain.model.Avatar
import com.example.movie.domain.model.MovieAccountState
import com.example.movie.domain.model.Tmdb
import com.example.movie.domain.model.UserDetail
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun mapUserDetail(user: UserDetailResponse): UserDetail {
        return UserDetail(
            avatar = user.avatar?.let { mapAvatar(it) },
            id = user.id,
            name = user.name,
            username = user.username
        )
    }
    fun mapAvatar(avatar: AvatarResponse): Avatar {
        return Avatar(
            tmdb = Tmdb(avatar.tmdb?.avatarPath)
        )
    }
    fun mapMovieAccountState(response: MovieAccountStateResponse): MovieAccountState {
        return MovieAccountState(
            id = response.id,
            favorite = response.favorite,
            rated = response.rated?.value,
            watchlist = response.watchlist
        )
    }
}