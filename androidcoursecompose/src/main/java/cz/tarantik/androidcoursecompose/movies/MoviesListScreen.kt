package cz.tarantik.androidcoursecompose.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import cz.tarantik.androidcoursecompose.model.Movie

@Composable
fun MoviesListScreen(moviesListViewModel: MoviesListViewModel = viewModel()) {
    val uiState by moviesListViewModel.uiState.collectAsState()

    MoviesList(movies = uiState.moviesList)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(movies){ movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(CutCornerShape(10.dp))
        ){
            Image(
                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(0.7f)
            )
        }
    }
}