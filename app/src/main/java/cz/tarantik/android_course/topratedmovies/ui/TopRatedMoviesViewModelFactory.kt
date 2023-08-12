package cz.tarantik.android_course.topratedmovies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.tarantik.android_course.topratedmovies.data.local.TopRatedMoviesDao
import java.lang.IllegalArgumentException

class TopRatedMoviesViewModelFactory(private val topRatedMoviesDao: TopRatedMoviesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopRatedMoviesViewModel::class.java)) {
            return TopRatedMoviesViewModel(topRatedMoviesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}