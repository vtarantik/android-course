package cz.tarantik.android_course.movieslist.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Types
import cz.tarantik.android_course.movieslist.data.remote.MoviesApi.moshi

class Converters {
    @TypeConverter
    fun listToJson(value: List<Int>): String {
        val type = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun listFromJson(value: String): List<Int>? {
        val type = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.fromJson(value)
    }
}