package cz.tarantik.android_course.movieslist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cz.tarantik.android_course.movieslist.datasource.JsonMovieDataSource
import cz.tarantik.android_course.movieslist.domain.model.Movie

class MoviesListViewModel(val app: Application): AndroidViewModel(app) {
    private lateinit var movies: List<Movie>

    fun loadMovies() {
        movies = JsonMovieDataSource(app.applicationContext).getPopularMovies()
    }

    fun getMovies() = movies
}