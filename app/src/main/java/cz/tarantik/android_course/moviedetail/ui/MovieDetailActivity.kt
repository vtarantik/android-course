package cz.tarantik.android_course.moviedetail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.ActivityMovieDetalBinding

const val ARG_MOVIE_ID = "movie_id"

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, movieId: Int) = Intent(context, MovieDetailActivity::class.java).apply {
            putExtra(ARG_MOVIE_ID, movieId)
        }
    }

    private lateinit var binding: ActivityMovieDetalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}