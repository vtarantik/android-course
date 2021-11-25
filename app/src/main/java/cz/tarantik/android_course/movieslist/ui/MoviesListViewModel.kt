package cz.tarantik.android_course.movieslist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity
import cz.tarantik.android_course.movieslist.data.local.MovieDao
import cz.tarantik.android_course.movieslist.data.mapper.PopularMovieMapper
import cz.tarantik.android_course.movieslist.domain.model.Movie
import cz.tarantik.android_course.movieslist.networking.MoviesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesListViewModel(private val movieDao: MovieDao) : ViewModel() {
    private val _uiState =
        MutableStateFlow<MoviesListUiState>(MoviesListUiState.Success(emptyList()))
    val uiState: StateFlow<MoviesListUiState> = _uiState
    val mapper = PopularMovieMapper()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val moviesResponse = MoviesApi.retrofitService.getPopularMovies(1)
                storeMovies(moviesResponse.results)
                val movies = moviesResponse.results.map {
                    mapper.mapToDomain(it)
                }
                _uiState.value = MoviesListUiState.Success(movies)
                Log.d("MLVM", moviesResponse.toString())
            } catch (e: Exception) {
                movieDao.getMovies().map { list -> list.map { mapper.mapToDomain(it) } }
                    .collect { movies ->
                        if (movies.isNotEmpty()) {
                            _uiState.value = MoviesListUiState.Success(movies)
                        } else {
                            Log.e("MLVM", e.message.toString())
                            _uiState.value = MoviesListUiState.Error(e)
                        }
                    }
            }
        }
    }

    private fun storeMovies(movies: List<PopularMovieEntity>) {
        viewModelScope.launch {
            movieDao.insertAll(movies)
        }
    }
}

sealed class MoviesListUiState {
    data class Success(val movies: List<Movie>) : MoviesListUiState()
    data class Error(val exception: Throwable) : MoviesListUiState()
}