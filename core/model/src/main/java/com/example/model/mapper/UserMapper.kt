package com.example.model.mapper

import com.example.model.model.Avatar
import com.example.model.model.MovieAccountState
import com.example.model.model.Tmdb
import com.example.model.model.UserDetail
import com.example.model.response.AvatarResponse
import com.example.model.response.MovieAccountStateResponse
import com.example.model.response.UserDetailResponse
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