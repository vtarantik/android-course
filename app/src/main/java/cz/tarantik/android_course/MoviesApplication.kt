package cz.tarantik.android_course

import android.app.Application
import cz.tarantik.android_course.movieslist.data.local.MoviesRoomDatabase

class MoviesApplication: Application() {
    val database: MoviesRoomDatabase by lazy {MoviesRoomDatabase.getDatabase(this)}
}