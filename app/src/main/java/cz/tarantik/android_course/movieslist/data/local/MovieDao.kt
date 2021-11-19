package cz.tarantik.android_course.movieslist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<PopularMovieEntity>)

    @Query("SELECT * from movie ORDER BY popularity DESC")
    fun getMovies(): Flow<List<PopularMovieEntity>>
}