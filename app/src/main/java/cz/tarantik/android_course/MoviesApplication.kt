package cz.tarantik.android_course

import android.app.Application
import cz.tarantik.android_course.movieslist.data.local.MovieRoomDatabase

class MoviesApplication : Application() {
    val database: MovieRoomDatabase by lazy { MovieRoomDatabase.getDatabase(this) }
}