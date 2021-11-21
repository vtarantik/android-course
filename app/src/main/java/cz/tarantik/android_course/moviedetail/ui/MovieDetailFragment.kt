package cz.tarantik.android_course.moviedetail.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.moviedetail.domain.model.MovieDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory((activity?.application as MoviesApplication).database.movieDetailDao(), args.movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is MovieDetailUiState.Success -> showDetail(value.movie)
                    is MovieDetailUiState.Error -> Log.e(
                        "MoviesListFragment",
                        value.exception.message.toString()
                    )
                    is MovieDetailUiState.Empty -> Log.d("MDF", "Data empty")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        view?.findViewById<YouTubePlayerView>(R.id.player)?.release()
    }

    private fun showDetail(movie: MovieDetail) {
        val tvMovieTitle = requireView().findViewById<TextView?>(R.id.tv_movie_title)
        val tvMovieDescription = requireView().findViewById<TextView?>(R.id.tv_movie_description)
        val player = requireView().findViewById<YouTubePlayerView>(R.id.player)
        val poster = requireView().findViewById<ImageView>(R.id.iv_movie_poster)

        tvMovieTitle?.text = movie.title
        tvMovieDescription?.text = movie.overview
        player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                Log.d("YTP", "Playing video: ${movie.videoId}")
                youTubePlayer.loadVideo(movie.videoId, 0F)
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    player.enterFullScreen()
                    hideSystemUI()
                } else {
                    player.exitFullScreen()
                }
            }
        })
        poster?.load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity?.window?.decorView?.systemUiVisibility = (
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}