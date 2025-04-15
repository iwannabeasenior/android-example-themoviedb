package com.example.model.response

import com.google.gson.annotations.SerializedName

data class VideoResponse (
    @SerializedName("iso_3166_1") val ios: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("site") val site: String?,
    @SerializedName("size") val size: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("official") val official: Boolean?,
    @SerializedName("published_at") val publishedAt: String?,
    @SerializedName("id") val id: String
)

/*
"iso_639_1": "en",
      "iso_3166_1": "US",
      "name": "\"Where is Snow White?\" Official Clip",
      "key": "Im0C6LAG3JM",
      "site": "YouTube",
      "size": 1080,
      "type": "Clip",
      "official": true,
      "published_at": "2025-03-21T21:30:02.000Z",
      "id": "67df5693978bda8ae24d875d"
 */