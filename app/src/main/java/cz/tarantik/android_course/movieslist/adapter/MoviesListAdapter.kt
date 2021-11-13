package cz.tarantik.android_course.movieslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.domain.model.Movie

class MoviesListAdapter :
    ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(MovieDiffCallback) {
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieNameTextView: TextView

        init {
            movieNameTextView = view.findViewById(R.id.tv_movie_name)
        }

        fun bind(movie: Movie) {
            movieNameTextView.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}