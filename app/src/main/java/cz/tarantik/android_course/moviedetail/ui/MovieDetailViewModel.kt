package cz.tarantik.android_course.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.tarantik.android_course.moviedetail.data.local.MovieDetailDBEntity
import cz.tarantik.android_course.moviedetail.data.local.MovieDetailDao
import cz.tarantik.android_course.moviedetail.domain.model.MovieDetail
import cz.tarantik.android_course.movieslist.data.remote.MoviesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MovieDetailViewModel(val movieDetailDao: MovieDetailDao, movieId: Int) : ViewModel() {
    // Backing property to avoid state updates from other classes
    private val _uiState =
        MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Empty)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieResponse = MoviesApi.retrofitService.getMovieDetail(movieId)
                val videosResponse = MoviesApi.retrofitService.getVideos(movieId)
                _uiState.value = MovieDetailUiState.Success(
                    MovieDetail(
                        movieResponse.title,
                        movieResponse.overview,
                        movieResponse.posterPath,
                        videosResponse.results[0].key,
                    )
                )
                movieDetailDao.insert(
                    MovieDetailDBEntity(
                        movieId,
                        movieResponse.title,
                        movieResponse.overview,
                        movieResponse.posterPath,
                        videosResponse.results[0].key
                    )
                )

            } catch (e: IOException) {
                val movieDetail = movieDetailDao.getMovieDetail(movieId)
                if (movieDetail != null) {
                    _uiState.value = MovieDetailUiState.Success(
                        MovieDetail(
                            movieDetail.title,
                            movieDetail.overview,
                            movieDetail.posterPath,
                            movieDetail.videoId,
                        )
                    )
                } else {
                    _uiState.value = MovieDetailUiState.Error(e)
                }
            }
        }
    }
}

sealed class MovieDetailUiState {
    data class Success(val movie: MovieDetail) : MovieDetailUiState()
    data class Error(val exception: Throwable) : MovieDetailUiState()
    object Empty : MovieDetailUiState()
}