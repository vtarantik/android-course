package cz.tarantik.android_course.movieslist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.adapter.MoviesListAdapter
import cz.tarantik.android_course.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private val viewModel: MoviesListViewModel by viewModels()
    private val moviesAdapter = MoviesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { value ->
                when (value) {
                    is MoviesListUiState.Success -> showMovies(value.movies)
                    is MoviesListUiState.Error -> Log.e(
                        "MoviesListFragment",
                        value.exception.message.toString()
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<RecyclerView>(R.id.recycler_movies_list).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }
}