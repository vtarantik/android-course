# android-course
1. Create .xml layout for fragment in res/layout named fragment_top.rated_movies
2. Add RecyclerView to the layout
3. Create .xml layout for list item in res/layout
4. Create a new package named topratedmovies inside the android_course package   
5. Create a Fragment class in /ui subpackage that uses the xml layout created in step 1
6. Jump to the **nav_graph.xml** and create a new destination (TopRatedMoviesFragment) 
7. Create an action leading from TRMF to existing MovieDetailFragment   
8. Inside of the fragment, create a **moviesAdapter** property (You can use MoviesAdapter we used previously) 
9. Use the TopRatedMoviesFragmentDirections to navigate to detail fragment using appropriate action on click on an item in the adapter creaeted previously 
10. Inside the Fragment, create a private function **showMovies** which will take list of movies as an input parameter and will call the **submitList** function on adapter passing the input list as a function parameter  
11. Create an entity for a response inside the data/entity subpackage(For MovieDB, you need two entities since an actual movie entity is wrapped in a response entity)
12. Check the MovieDB documentation for the required path to receive a desired list of movies
13. Create a new function in MoviesApiService using the @Get annotation returning a response entity
14. Create a ViewModel class in the /ui subpackage
15. Inside ViewModel file after the closing bracket of VM class, create TopRatedMoviesUiState sealed class
16. Inside the TopRatedMoviesUiState sealed class create two data classes (One for success that will hold list of movies as a value, the other for error that will hold the exception as a value) both data classes must inherit from the sealed class
17. Inside ViewModel class, create a **state** and **_state** properties for holding the state 
18. Inside ViewModel, create an empty function that will load the data from the network
19. Inside the function created in the previous step,launch the coroutine for a viewmodel scope    
20. Inside the coroutine created in the previous step, get the data from MoviesApi via retrofit service
21. Map the data from previous step using Mapper and map function into the Movie class
22. Save the output of previous step into the _state
23. Go to the Fragment class and create a viewModel property using the **by viewModels** delegate    
24. In fragment class, override onCreate(savedInstanceState: Bundle?) function
25. Inside the onCreate function, launch the coroutine for a lifecycle scope
26. Inside the coroutine, subscribe to the uiState property of VM using the **collect** function
27. If the data is received inside of the collect block, call **showMovies** function
28. If the error is received, log the error
29. Run the app!
