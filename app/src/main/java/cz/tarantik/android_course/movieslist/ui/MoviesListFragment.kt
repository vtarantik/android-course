package cz.tarantik.android_course.movieslist.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.MoviesApplication
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory((activity?.application as MoviesApplication)
            .database.movieDao())
    }
    val moviesAdapter = MoviesListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = moviesAdapter
            val currentOrientation = activity?.resources?.configuration?.orientation
            layoutManager = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                LinearLayoutManager(requireContext())
            }else {
                GridLayoutManager(requireContext(),2)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is MoviesListUiState.Success -> showMovies(value.movies)
                    is MoviesListUiState.Error -> Log.e("FML", value.exception.toString())
                }
            }
        }
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }
}