package cz.tarantik.androidcoursecompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}