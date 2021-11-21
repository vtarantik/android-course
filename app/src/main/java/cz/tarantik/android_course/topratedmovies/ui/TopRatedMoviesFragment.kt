package cz.tarantik.android_course.topratedmovies.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.domain.model.Movie
import cz.tarantik.android_course.topratedmovies.adapter.TopRatedMoviesAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TopRatedMoviesFragment : Fragment(R.layout.fragment_top_rated_movies) {
    private val viewModel: TopRatedMoviesViewModel by viewModels {
        TopRatedMoviesViewModelFactory((activity?.application as MoviesApplication).database.topRatedMoviesDao())
    }
    private val moviesAdapter = TopRatedMoviesAdapter {
        val action = TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is TopRatedMoviesUiState.Success -> showMovies(value.movies)
                    is TopRatedMoviesUiState.Error -> Log.e(
                        "TopRatedMoviesFragment",
                        value.exception.message.toString()
                    )
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<RecyclerView>(R.id.recycler_movies_list).apply {
            layoutManager =
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LinearLayoutManager(requireContext())
                } else {
                    GridLayoutManager(requireContext(), 2)
                }
            adapter = moviesAdapter
        }
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }
}