package cz.tarantik.android_course.movieslist.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.datasource.JsonMovieDataSource

const val COUNTER_VALUE = "counter_value"
class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesAdapter = MoviesListAdapter()
        val moviesDataSource = JsonMovieDataSource(activity!!.applicationContext)

        requireView().findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(activity!!.applicationContext)
        }
        moviesAdapter.submitList(moviesDataSource.getPopularMovies())

        val tvCounter =  requireView().findViewById<TextView>(R.id.tv_counter)
        savedInstanceState?.let { bundle ->
            tvCounter.text = bundle.getInt(COUNTER_VALUE).toString()
        }

        requireView().findViewById<Button>(R.id.btn_counter).setOnClickListener {
            tvCounter.text = getCounterValue().plus(1).toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(COUNTER_VALUE,getCounterValue())
    }

    private fun getCounterValue(): Int {
        val tvCounter = requireView().findViewById<TextView>(R.id.tv_counter)
        return if (tvCounter.text.isEmpty()) 0 else tvCounter.text.toString().toInt()
    }
}