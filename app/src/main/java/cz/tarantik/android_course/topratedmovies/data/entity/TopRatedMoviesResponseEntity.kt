package cz.tarantik.android_course.topratedmovies.data.entity

import com.squareup.moshi.Json

data class TopRatedMoviesResponseEntity(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<TopRatedMovieEntity>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)