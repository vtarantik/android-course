package cz.tarantik.android_course.topratedmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.tarantik.android_course.topratedmovies.data.entity.TopRatedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopRatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<TopRatedMovieEntity>)

    @Query("SELECT * from top_rated ORDER BY vote_average DESC")
    fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>
}