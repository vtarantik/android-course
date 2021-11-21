package cz.tarantik.android_course.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.tarantik.android_course.moviedetail.data.local.MovieDetailDao

class MovieDetailViewModelFactory(private val movieDetailDao: MovieDetailDao, private val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieDetailDao, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}