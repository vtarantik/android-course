package cz.tarantik.android_course.moviedetail.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentMovieDetailBinding
import cz.tarantik.android_course.moviedetail.domain.model.MovieDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(
            (activity?.application as MoviesApplication).database.movieDetailDao(),
            args.movieId
        )
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        view?.findViewById<YouTubePlayerView>(R.id.player)?.release()
    }

    private fun showDetail(movie: MovieDetail) {
        binding.tvMovieTitle?.text = movie.title
        binding.tvMovieDescription?.text = movie.overview
        binding.player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                Log.d("YTP", "Playing video: ${movie.videoId}")
                youTubePlayer.loadVideo(movie.videoId, 0F)
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    binding.player.enterFullScreen()
                    hideSystemUI()
                } else {
                    binding.player.exitFullScreen()
                    showSystemUI()
                }
            }
        })
        (requireActivity() as AppCompatActivity).supportActionBar?.title = movie.title
        binding.ivMoviePoster?.load("https://image.tmdb.org/t/p/w500${movie.posterPath}")

    }


    private fun hideSystemUI() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE
        val bottomBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.visibility = View.GONE
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

    private fun showSystemUI() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE
        val bottomBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.visibility = View.VISIBLE
    }
}