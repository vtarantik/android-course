package cz.tarantik.android_course.topratedmovies.data.mapper

import cz.tarantik.android_course.movieslist.data.mapper.UnsupportedMappingException
import cz.tarantik.android_course.movieslist.domain.model.Movie
import cz.tarantik.android_course.topratedmovies.data.entity.TopRatedMovieEntity

class TopRatedMovieMapper :
    cz.tarantik.android_course.movieslist.data.mapper.Mapper<Movie, TopRatedMovieEntity> {
    override fun mapToDomain(entity: TopRatedMovieEntity): Movie {
        return Movie(
            entity.backdropPath,
            entity.id,
            entity.title,
            entity.overview,
            entity.releaseDate,
            entity.posterPath
        )
    }

    override fun mapToEntity(domain: Movie): TopRatedMovieEntity =
        throw UnsupportedMappingException()
}