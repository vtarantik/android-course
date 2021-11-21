package cz.tarantik.android_course.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.tarantik.android_course.moviedetail.data.local.MovieDetailDBEntity
import cz.tarantik.android_course.moviedetail.data.local.MovieDetailDao
import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity
import cz.tarantik.android_course.movieslist.data.local.MovieDao
import cz.tarantik.android_course.topratedmovies.data.entity.TopRatedMovieEntity
import cz.tarantik.android_course.topratedmovies.data.local.TopRatedMoviesDao

@Database(entities = [PopularMovieEntity::class, MovieDetailDBEntity::class, TopRatedMovieEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun topRatedMoviesDao(): TopRatedMoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}