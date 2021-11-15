package cz.tarantik.android_course.movieslist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.adapter.MoviesListAdapter

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    val viewModel: MoviesListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesAdapter = MoviesListAdapter()

        requireView().findViewById<RecyclerView>(R.id.recycler_movies_list).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        viewModel.loadMovies()
        moviesAdapter.submitList(viewModel.getMovies())
    }
}