package cz.tarantik.android_course.movieslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.tarantik.android_course.movieslist.data.local.MovieDao
import java.lang.IllegalArgumentException

class MoviesListViewModelFactory(private val movieDao: MovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
            return MoviesListViewModel(movieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}