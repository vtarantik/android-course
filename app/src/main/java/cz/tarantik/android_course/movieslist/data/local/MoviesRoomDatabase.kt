package cz.tarantik.android_course.movieslist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity

@Database(entities = [PopularMovieEntity::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesRoomDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesRoomDatabase? = null

        fun getDatabase(context:Context): MoviesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesRoomDatabase::class.java,
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