package cz.tarantik.android_course.movieslist.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.tarantik.android_course.BuildConfig
import cz.tarantik.android_course.movieslist.data.entity.PopularMoviesResponseEntity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(MoviesApi.moshi))
    .baseUrl(BuildConfig.API_BASE_URL)
    .client(MoviesApi.okHttpClient)
    .build()

private fun provideQueryInterceptor(): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url()

    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", "5e7984851d42c7b8929f14cedf753ed9")
        .build()

    val requestBuilder = original.newBuilder().url(url)

    val request = requestBuilder.build()

    chain.proceed(request)
}

interface MoviesApiService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): PopularMoviesResponseEntity
}

private const val CONNECTION_TIMEOUT = 30L

object MoviesApi {
    val retrofitService: MoviesApiService by lazy { retrofit.create(MoviesApiService::class.java) }
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
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