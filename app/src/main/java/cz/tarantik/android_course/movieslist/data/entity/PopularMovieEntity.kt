package cz.tarantik.android_course.movieslist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "movies")
data class PopularMovieEntity(
    @Json(name="id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Json(name = "adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "genre_ids")
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @Json(name="original_title")
    @ColumnInfo(name="original_title")
    val originalTitle: String,
    @Json(name="overview")
    @ColumnInfo(name="overview")
    val overview: String,
    @Json(name="popularity")
    @ColumnInfo(name="popularity")
    val popularity: Double,
    @Json(name="poster_path")
    @ColumnInfo(name="poster_path")
    val posterPath: String,
    @Json(name="release_date")
    @ColumnInfo(name="release_date")
    val releaseDate: String,
    @Json(name="title")
    @ColumnInfo(name="title")
    val title: String,
    @Json(name = "video")
    @ColumnInfo(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
)