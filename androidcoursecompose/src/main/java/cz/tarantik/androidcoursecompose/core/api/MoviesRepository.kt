package cz.tarantik.androidcoursecompose.core.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor (private val api: MoviesApi, private val mapper: MovieMapper) {
    suspend fun getMovies() = api.getPopularMovies().results.map { entity -> mapper.mapToDomain(entity) }
}