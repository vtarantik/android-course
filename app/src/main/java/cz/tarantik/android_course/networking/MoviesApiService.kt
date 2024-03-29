package cz.tarantik.android_course.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.tarantik.android_course.BuildConfig
import cz.tarantik.android_course.moviedetail.data.remote.MovieDetailEntity
import cz.tarantik.android_course.moviedetail.data.remote.VideosResponseEntity
import cz.tarantik.android_course.movieslist.data.entity.PopularMoviesResponseEntity
import cz.tarantik.android_course.topratedmovies.data.entity.TopRatedMoviesResponseEntity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .client(MoviesApi.okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(MoviesApi.moshi))
    .build()

private fun provideQueryInterceptor(): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url

    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", BuildConfig.API_KEY)
        .build()

    // Request customization: add request headers
    val requestBuilder = original.newBuilder().url(url)

    val request = requestBuilder.build()
    chain.proceed(request)
}

private const val CONNECTION_TIMEOUT = 30L

object MoviesApi {
    val retrofitService: MoviesApiService by lazy { retrofit.create(MoviesApiService::class.java) }
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(provideQueryInterceptor())
        }.build()
    }
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): PopularMoviesResponseEntity

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailEntity

    @GET("movie/{movieId}/videos")
    suspend fun getVideos(@Path("movieId") movieId: Int): VideosResponseEntity

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): TopRatedMoviesResponseEntity
}