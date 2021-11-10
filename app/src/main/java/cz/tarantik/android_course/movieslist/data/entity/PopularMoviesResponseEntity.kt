package cz.tarantik.android_course.movieslist.data.entity

data class PopularMoviesResponseEntity(
    val page: Int,
    val results: List<PopularMovieEntity>,
    val totalPages: Int,
    val totalResults: Int,
)