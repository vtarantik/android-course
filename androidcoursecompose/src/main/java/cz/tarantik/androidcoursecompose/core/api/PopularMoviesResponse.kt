package cz.tarantik.androidcoursecompose.core.api

import com.squareup.moshi.Json

data class PopularMoviesResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<PopularMovieEntity>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
)