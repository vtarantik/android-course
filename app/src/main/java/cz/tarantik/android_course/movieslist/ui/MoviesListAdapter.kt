package cz.tarantik.android_course.movieslist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import cz.tarantik.android_course.R
import cz.tarantik.android_course.movieslist.domain.model.Movie

class MoviesListAdapter: ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(MovieDiffCallback) {
    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val moviePosterImageView: ImageView = view.findViewById(R.id.iv_movie_poster)
        private val movieTitleTextView: TextView? = view.findViewById(R.id.tv_movie_name)
        private val movieDescriptionTextView: TextView? = view.findViewById(R.id.tv_movie_description)

        fun bind(movie: Movie){
            moviePosterImageView.load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            movieTitleTextView?.text = movie.title
            movieDescriptionTextView?.text = movie.overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}
object MovieDiffCallback: DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

}