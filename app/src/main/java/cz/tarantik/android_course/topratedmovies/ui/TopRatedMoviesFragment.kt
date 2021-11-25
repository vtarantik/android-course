package cz.tarantik.android_course.topratedmovies.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentTopRatedMoviesBinding
import cz.tarantik.android_course.movieslist.adapter.MoviesListAdapter
import cz.tarantik.android_course.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TopRatedMoviesFragment : Fragment(R.layout.fragment_top_rated_movies) {
    private val viewModel: TopRatedMoviesViewModel by viewModels {
        TopRatedMoviesViewModelFactory((activity?.application as MoviesApplication).database.topRatedMoviesDao())
    }
    private val moviesAdapter = MoviesListAdapter {
        val action = TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it)
        findNavController().navigate(action)
    }

    private var _binding: FragmentTopRatedMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is TopRatedMoviesUiState.Success -> {
                        binding.layoutEmpty.root.visibility = View.GONE
                        binding.recyclerMoviesList.visibility = View.VISIBLE
                        showMovies(value.movies)
                    }
                    is TopRatedMoviesUiState.Error -> {
                        Log.e(
                            "TopRatedMoviesFragment",
                            value.exception.message.toString()
                        )
                        binding.layoutEmpty.root.visibility = View.VISIBLE
                        binding.recyclerMoviesList.visibility = View.GONE
                    }
                }

            }
        }

        binding.recyclerMoviesList?.apply {
            layoutManager =
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LinearLayoutManager(requireContext())
                } else {
                    GridLayoutManager(requireContext(), 2)
                }
            adapter = moviesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }
}