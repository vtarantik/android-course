package cz.tarantik.android_course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import cz.tarantik.android_course.moviedetail.ui.EXTRA_MOVIE_ID_TAG
import cz.tarantik.android_course.moviedetail.ui.MovieDetailFragment
import cz.tarantik.android_course.movieslist.ui.MoviesListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main),
    MoviesListFragment.MoviesListNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MoviesListFragment>(R.id.container)
            }
        }

    }

    override fun showMovieDetail(id: Int) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MovieDetailFragment>(R.id.container, args = Bundle().apply {
                putInt(EXTRA_MOVIE_ID_TAG, id)
            })
        }
    }

}