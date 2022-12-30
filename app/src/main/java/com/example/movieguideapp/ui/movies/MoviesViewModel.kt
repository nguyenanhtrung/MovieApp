package com.example.movieguideapp.ui.movies

import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.domain.movie.GetMoviesUseCase
import com.example.movieguideapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    BaseViewModel() {

    var movieType: MovieType? = null

    private val _movies: MutableStateFlow<List<MovieItemData>> = MutableStateFlow(listOf())
    internal val moviesFlow: StateFlow<List<MovieItemData>>
        get() = _movies

    internal fun loadMovies(page: Int) {
        movieType?.let {
            executeUseCase(
                param = Pair(page, it),
                useCase = getMoviesUseCase,
                isShowLoading = page == 0,
                onSuccess = {

                }
            )
        }
    }

}