package cz.tarantik.android_course.movieslist.ui

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
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentMoviesListBinding
import cz.tarantik.android_course.movieslist.adapter.MoviesListAdapter
import cz.tarantik.android_course.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory((activity?.application as MoviesApplication).database.movieDao())
    }
    private val moviesAdapter = MoviesListAdapter {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(it)
        findNavController().navigate(action)
    }
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is MoviesListUiState.Success -> {
                        binding.layoutEmpty.root.visibility = View.GONE
                        binding.recyclerMoviesList.visibility = View.VISIBLE
                        showMovies(value.movies)
                    }
                    is MoviesListUiState.Error -> {
                        Log.e(
                            "MoviesListFragment",
                            value.exception.message.toString()
                        )
                        binding.layoutEmpty.root.visibility = View.VISIBLE
                        binding.recyclerMoviesList.visibility = View.GONE
                    }
                }
            }
        }

        binding.recyclerMoviesList.apply {
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