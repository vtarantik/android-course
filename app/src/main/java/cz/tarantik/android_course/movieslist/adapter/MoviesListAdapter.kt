package cz.tarantik.android_course.movieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import cz.tarantik.android_course.databinding.ItemMoviesListBinding
import cz.tarantik.android_course.movieslist.domain.model.Movie

class MoviesListAdapter(private val onMovieClicked: (movieId: Int) -> Unit) :
    ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(
        private val binding: ItemMoviesListBinding,
        private val onMovieClicked: (movieId: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvMovieName?.text = movie.title
            binding.tvMovieDescription?.text = movie.overview
            binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            binding.root.setOnClickListener { onMovieClicked(movie.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMoviesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onMovieClicked
        )
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