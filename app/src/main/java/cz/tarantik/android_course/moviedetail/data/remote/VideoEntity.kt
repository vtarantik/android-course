package cz.tarantik.android_course.moviedetail.data.remote

import com.squareup.moshi.Json

data class VideoEntity(
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @Json(name = "published_at")
    val publishedAt: String,
    val id: String
)