package cz.tarantik.android_course.moviedetail.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import cz.tarantik.android_course.R

const val EXTRA_MOVIE_ID_TAG = "extra_movie_id"
class MovieDetailFragment: Fragment(R.layout.fragment_movie_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MDF", "Args received: ${arguments?.getInt(EXTRA_MOVIE_ID_TAG)}")
    }
}