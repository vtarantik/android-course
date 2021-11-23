import com.squareup.moshi.Json

data class MovieDetailEntity(
    val adult: Boolean,
    val id: Int,
    @Json(name = "backdrop_path")
    val backDropPath: String,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: CollectionEntity?,
    val budget: Int,
    val genres: List<GenreEntity>,
    val homepage: String,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyEntity>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountryEntity>,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageEntity>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

data class CollectionEntity(
    val id: Int,
    val name: String,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
)

data class GenreEntity(
    val id: Int,
    val name: String,
)

data class ProductionCompanyEntity(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String,
)

data class ProductionCountryEntity(
    val iso_3166_1: String,
    val name: String,
)

data class SpokenLanguageEntity(
    val iso_639_1: String,
    val name: String,
)