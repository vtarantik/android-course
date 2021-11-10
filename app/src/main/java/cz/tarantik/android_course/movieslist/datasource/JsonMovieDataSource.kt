package cz.tarantik.android_course.movieslist.datasource

import android.content.Context
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity
import cz.tarantik.android_course.movieslist.data.mapper.PopularMovieMapper
import cz.tarantik.android_course.movieslist.domain.model.Movie
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class JsonMovieDataSource(private val context: Context) : MovieDataSource {
    private val mapper = PopularMovieMapper()

    override fun getPopularMovies(): List<Movie> {
        val favouriteMovies = mutableListOf<Movie>()
        val bufferedReader =
            BufferedReader(InputStreamReader(context.resources.openRawResource(R.raw.popular_movies)))
        val popularMoviesResponseJson = JSONObject(bufferedReader.readText())
        val popularMoviesJsonArray = popularMoviesResponseJson.getJSONArray("results")
        for (i in 0 until popularMoviesJsonArray.length()) {
            val popularMovieJson = popularMoviesJsonArray.getJSONObject(i)
            val genreIds = mutableListOf<Int>()
            for (j in 0 until popularMovieJson.getJSONArray("genre_ids").length()) {
                genreIds.add(popularMovieJson.getJSONArray("genre_ids")[j] as Int)
            }
            favouriteMovies.add(
                mapper.mapToDomain(
                    PopularMovieEntity(
                        popularMovieJson.getBoolean("adult"),
                        popularMovieJson.getString("backdrop_path"),
                        genreIds,
                        popularMovieJson.getInt("id"),
                        popularMovieJson.getString("original_language"),
                        popularMovieJson.getString("original_title"),
                        popularMovieJson.getString("overview"),
                        popularMovieJson.getDouble("popularity"),
                        popularMovieJson.getString("poster_path"),
                        popularMovieJson.getString("release_date"),
                        popularMovieJson.getString("title"),
                        popularMovieJson.getBoolean("video"),
                        popularMovieJson.getDouble("vote_average"),
                        popularMovieJson.getInt("vote_count")
                    )
                )
            )
        }
        return favouriteMovies
    }
}