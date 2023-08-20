package cz.tarantik.android_course.di

import androidx.room.Room.databaseBuilder
import cz.tarantik.android_course.database.MovieRoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    db()
}

private fun Module.db() {
    // Database
    single {
        databaseBuilder(
                androidApplication(),
                MovieRoomDatabase::class.java,
                MovieRoomDatabase.Name
            )
            .build()
    }

    // Dao
    single { get<MovieRoomDatabase>().movieDetailDao() }
    single { get<MovieRoomDatabase>().movieDao() }
    single { get<MovieRoomDatabase>().topRatedMoviesDao() }
}