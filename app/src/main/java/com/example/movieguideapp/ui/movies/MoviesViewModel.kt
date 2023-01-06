package com.example.movieguideapp.ui.movies

import androidx.lifecycle.viewModelScope
import com.example.movieguideapp.data.local.model.SingleLiveEvent
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.local.model.movie.MoviesEvent
import com.example.movieguideapp.domain.movie.GetMoviesUseCase
import com.example.movieguideapp.domain.moviedetail.GetMovieDetailUseCase
import com.example.movieguideapp.extensions.updateState
import com.example.movieguideapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) :
    BaseViewModel() {

    var movieType: MovieType? = null

    private val _movies: MutableStateFlow<List<MovieItemData>> = MutableStateFlow(listOf())
    internal val moviesFlow: StateFlow<List<MovieItemData>>
        get() = _movies

    private val _isSearchResultFound: MutableStateFlow<Boolean> = MutableStateFlow(true)
    internal val isSearchResultFound: StateFlow<Boolean>
        get() = _isSearchResultFound

    private val movieEvent: SingleLiveEvent<MoviesEvent> = SingleLiveEvent()
    internal val movieEventFlow = movieEvent.eventFlow


    internal fun loadMovies(page: Int = 1) {
        movieType?.let {
            executeUseCase(
                param = Pair(page, it),
                useCase = getMoviesUseCase,
                isShowLoading = page == 0,
                onSuccess = { data ->
                    _movies.updateState { currentState ->
                        val list = currentState.toMutableList()
                        list.addAll(data)
                        list.toList()
                    }
                }
            )
        }
    }

    internal fun onClickMovieItem(position: Int) {
        val movieList = _movies.value
        val selectedMovie = movieList[position]

        executeUseCase(
            param = selectedMovie.id,
            useCase = getMovieDetailUseCase,
            isShowLoading = true,
            onSuccess = {
                viewModelScope.launch {
                    movieEvent.send(MoviesEvent.MovieDetailNavigation(it))
                }
            }
        )
    }

    internal fun onMovieItemFiltered(query: String?, hasResult: Boolean) {
        viewModelScope.launch {
            if (!query.isNullOrEmpty()) {
                if (_isSearchResultFound.value != hasResult) {
                    _isSearchResultFound.value = hasResult
                }
            }
        }
    }

    internal fun onMovieQueryEmpty() {
        viewModelScope.launch {
            _isSearchResultFound.value = true
        }
    }

}