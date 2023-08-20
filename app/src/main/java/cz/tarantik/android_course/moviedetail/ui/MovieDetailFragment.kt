package cz.tarantik.android_course.moviedetail.ui

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentMovieDetailBinding
import cz.tarantik.android_course.moviedetail.domain.model.MovieDetail
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var videoId: String

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

        Log.d("ASD", "onViewCreated")

        val movieId = activity?.intent?.extras?.getInt(ARG_MOVIE_ID) ?: 0

        val viewModel: MovieDetailViewModel by viewModel { parametersOf(movieId) }

        binding.groupOffline.visibility = View.GONE

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is MovieDetailUiState.Success -> {
                        Log.d("ASD", "data load success")
                        binding.groupOffline.visibility = View.GONE
                        binding.player.visibility = View.VISIBLE
                        videoId = value.movie.videoId
                        showDetail(value.movie)
                    }

                    is MovieDetailUiState.Error -> Log.e(
                        "MoviesListFragment",
                        value.exception.message.toString()
                    )

                    is MovieDetailUiState.Empty -> {
                        Log.d("MDF", "Data empty")
                    }

                    is MovieDetailUiState.Offline -> {
                        binding.groupOffline.visibility = View.VISIBLE
                        binding.player.visibility = View.INVISIBLE
                        showDetail(value.movie)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.player.release()
        _binding = null
    }

    override fun onConfigurationChanged(configuration: Configuration) {
        super.onConfigurationChanged(configuration)

        // Checks the orientation of the screen
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()

        } else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUI()
        }
    }

    private fun showDetail(movie: MovieDetail) {
        binding.tvMovieTitle?.text = movie.title
        binding.tvMovieDescription?.text = movie.overview

        binding.player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                Log.d("YTP", "Playing video: ${movie.videoId}")
                youTubePlayer.loadVideo(movie.videoId, 0F)
            }
        })

        lifecycle.addObserver(binding.player)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = movie.title
        binding.ivMoviePoster?.load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
    }


    private fun hideSystemUI() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE
        val bottomBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.visibility = View.GONE

        if (Build.VERSION.SDK_INT >= 30) {
            binding.player.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        }
    }

    private fun showSystemUI() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE
        val bottomBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.visibility = View.VISIBLE
    }
}