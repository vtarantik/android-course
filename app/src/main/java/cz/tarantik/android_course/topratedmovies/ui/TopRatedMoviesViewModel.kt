package cz.tarantik.android_course.topratedmovies.ui

import cz.tarantik.android_course.base.BaseViewModel
import cz.tarantik.android_course.movieslist.domain.model.Movie
import cz.tarantik.android_course.networking.MoviesApi
import cz.tarantik.android_course.topratedmovies.data.entity.TopRatedMovieEntity
import cz.tarantik.android_course.topratedmovies.data.local.TopRatedMoviesDao
import cz.tarantik.android_course.topratedmovies.data.mapper.TopRatedMovieMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class TopRatedMoviesViewModel(private val topRatedMoviesDao: TopRatedMoviesDao) : BaseViewModel() {
    // Backing property to avoid state updates from other classes
    private val _uiState =
        MutableStateFlow<TopRatedMoviesUiState>(TopRatedMoviesUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<TopRatedMoviesUiState> = _uiState
    private var topRatedMapper = TopRatedMovieMapper()

    init {
        loadMovies()
    }

    private fun storeMovies(movies: List<TopRatedMovieEntity>) {
        launch {
            topRatedMoviesDao.insertAll(movies)
        }
    }

    private fun loadMovies() {
        launch {
            try {
                val moviesResponse = MoviesApi.retrofitService.getTopRatedMovies(1)
                storeMovies(moviesResponse.results)
                val movies = moviesResponse.results.map {
                    topRatedMapper.mapToDomain(it)
                }
                _uiState.value = TopRatedMoviesUiState.Success(movies)
            } catch (e: IOException) {
                topRatedMoviesDao.getTopRatedMovies()
                    .map { list -> list.map { topRatedMapper.mapToDomain(it) } }.collect { movies ->
                        if (movies.isNotEmpty()) {
                            _uiState.value = TopRatedMoviesUiState.Success(movies)
                        } else {
                            _uiState.value = TopRatedMoviesUiState.Error(e)
                        }
                    }
            }
        }
    }
}

sealed class TopRatedMoviesUiState {
    data class Success(val movies: List<Movie>) : TopRatedMoviesUiState()
    data class Error(val exception: Throwable) : TopRatedMoviesUiState()
}