package cz.tarantik.android_course

import android.app.Application
import cz.tarantik.android_course.database.MovieRoomDatabase
import cz.tarantik.android_course.di.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    val database: MovieRoomDatabase by lazy { MovieRoomDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(applicationModules)
        }
    }
}