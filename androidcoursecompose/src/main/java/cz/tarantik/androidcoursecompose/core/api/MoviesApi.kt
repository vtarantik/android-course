package cz.tarantik.androidcoursecompose.core.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): PopularMoviesResponse

}