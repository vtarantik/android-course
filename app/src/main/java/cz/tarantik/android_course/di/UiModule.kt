package cz.tarantik.android_course.di

import cz.tarantik.android_course.moviedetail.ui.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {parameters ->  MovieDetailViewModel(get(), movieId = parameters.get()) }
}