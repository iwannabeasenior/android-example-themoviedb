package com.example.model.request

import com.google.gson.annotations.SerializedName

class MediaIdParams(
    @SerializedName("media_id") val mediaId: Int
): Params()