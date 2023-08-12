package cz.tarantik.android_course.moviedetail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieDetail: MovieDetailDBEntity)

    @Query("SELECT * from movie_detail WHERE id = :id")
    fun getMovieDetail(id: Int): MovieDetailDBEntity?
}