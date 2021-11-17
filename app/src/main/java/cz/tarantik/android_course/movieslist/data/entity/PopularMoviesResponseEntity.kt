package cz.tarantik.android_course.movieslist.data.entity

import com.squareup.moshi.Json

data class PopularMoviesResponseEntity(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<PopularMovieEntity>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)