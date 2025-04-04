package com.example.movie.data.request

import com.google.gson.annotations.SerializedName

class MediaIdParams(
    @SerializedName("media_id") val mediaId: Int
): Params()