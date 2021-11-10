package cz.tarantik.android_course.movieslist.data.mapper

import cz.tarantik.android_course.movieslist.data.entity.PopularMovieEntity
import cz.tarantik.android_course.movieslist.domain.model.Movie

class PopularMovieMapper : Mapper<Movie, PopularMovieEntity> {
    override fun mapToDomain(entity: PopularMovieEntity): Movie {
        return Movie(
            entity.backdropPath,
            entity.id,
            entity.title,
            entity.overview,
            entity.releaseDate,
            entity.posterPath
        )
    }

    override fun mapToEntity(domain: Movie): PopularMovieEntity = throw UnsupportedMappingException()
}