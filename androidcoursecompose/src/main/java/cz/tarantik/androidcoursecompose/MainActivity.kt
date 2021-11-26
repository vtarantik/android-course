package cz.tarantik.androidcoursecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cz.tarantik.androidcoursecompose.movies.MoviesListScreen
import cz.tarantik.androidcoursecompose.ui.theme.AndroidcourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MoviesListScreen()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    AndroidcourseTheme {
        Surface(color = MaterialTheme.colors.surface) {
            content()
        }
    }
}

@Preview(showBackground = true, name = "Movie preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MoviesListScreen()
    }
}