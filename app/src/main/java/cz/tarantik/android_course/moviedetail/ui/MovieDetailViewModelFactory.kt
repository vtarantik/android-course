package cz.tarantik.android_course.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}