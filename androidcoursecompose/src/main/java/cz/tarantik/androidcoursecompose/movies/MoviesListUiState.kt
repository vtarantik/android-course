package cz.tarantik.androidcoursecompose.movies

import cz.tarantik.androidcoursecompose.model.Movie

data class MoviesListUiState (
    val moviesList: List<Movie> = emptyList(),
    val loading: Boolean = false,
    val refreshError: Boolean = false,
)