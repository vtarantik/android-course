package cz.tarantik.android_course.movieslist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.adapter.MoviesListAdapter
import cz.tarantik.android_course.movieslist.datasource.JsonMovieDataSource

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesAdapter = MoviesListAdapter()
        val moviesDataSource = JsonMovieDataSource(requireContext())

        requireView().findViewById<RecyclerView>(R.id.recycler_movies_list).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        moviesAdapter.submitList(moviesDataSource.getPopularMovies())
    }
}