package cz.tarantik.android_course.movieslist.domain.model

data class Movie(
    val backdropPath: String,
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
)