package cz.tarantik.androidcoursecompose.model

import java.util.*

data class Movie(
    val posterPath: String,
    val adult: Boolean,
    val overview: String,
    val releaseDate: Date,
    val genreIds: List<Int>,
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Double,
)