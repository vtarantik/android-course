package cz.tarantik.androidcoursecompose.movies

import cz.tarantik.android_course.base.BaseViewModel
import cz.tarantik.androidcoursecompose.core.api.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): BaseViewModel() {

    private val _uiState = MutableStateFlow(MoviesListUiState(loading = true))
    val uiState = _uiState.asStateFlow()


    init {
        launch {
            val movies = moviesRepository.getMovies()
            _uiState.value = MoviesListUiState(moviesList = movies)
        }
    }
}