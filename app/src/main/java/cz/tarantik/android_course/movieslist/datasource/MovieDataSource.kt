package cz.tarantik.android_course.movieslist.datasource

import cz.tarantik.android_course.movieslist.domain.model.Movie

interface MovieDataSource {
    fun getPopularMovies(): List<Movie>
}