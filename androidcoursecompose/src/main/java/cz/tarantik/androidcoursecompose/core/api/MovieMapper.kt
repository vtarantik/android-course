package cz.tarantik.androidcoursecompose.core.api

import cz.tarantik.androidcoursecompose.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMapper @Inject constructor() {

    fun mapToDomain(entity: PopularMovieEntity): Movie {
        return Movie(
            entity.posterPath,
            entity.adult,
            entity.overview,
            entity.releaseDate,
            entity.genreIds,
            entity.id,
            entity.originalTitle,
            entity.originalLanguage,
            entity.title,
            entity.backdropPath,
            entity.popularity,
            entity.voteCount,
            entity.video,
            entity.voteAverage,
        )
    }
}