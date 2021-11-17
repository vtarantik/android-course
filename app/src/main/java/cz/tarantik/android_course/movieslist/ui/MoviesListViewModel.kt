package cz.tarantik.android_course.movieslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.tarantik.android_course.movieslist.data.mapper.PopularMovieMapper
import cz.tarantik.android_course.movieslist.data.remote.MoviesApi
import cz.tarantik.android_course.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {
    // Backing property to avoid state updates from other classes
    private val _uiState =
        MutableStateFlow<MoviesListUiState>(MoviesListUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<MoviesListUiState> = _uiState
    private var movieMapper = PopularMovieMapper()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val moviesResponse = MoviesApi.retrofitService.getPopularMovies(1)
                val movies = moviesResponse.results.map {
                    movieMapper.mapToDomain(it)
                }
                _uiState.value = MoviesListUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MoviesListUiState.Error(e)
            }
        }
    }
}

sealed class MoviesListUiState {
    data class Success(val movies: List<Movie>) : MoviesListUiState()
    data class Error(val exception: Throwable) : MoviesListUiState()
}