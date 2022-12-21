package com.example.movieguideapp.ui.moviehome

import androidx.lifecycle.viewModelScope
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageEvent
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.local.model.movie.MovieHomeUiState
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.genre.GetMovieGenresUseCase
import com.example.movieguideapp.domain.movie.GetMovieBannersUseCase
import com.example.movieguideapp.domain.movie.GetMoviesUseCase
import com.example.movieguideapp.domain.moviedetail.GetMovieDetailUseCase
import com.example.movieguideapp.extensions.update
import com.example.movieguideapp.extensions.updateState
import com.example.movieguideapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieHomePageViewModel @Inject constructor(
    private val getMovieBannersUseCase: GetMovieBannersUseCase,
    private val getGenresUseCase: GetMovieGenresUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val movieRepository: MovieRepository
) :
    BaseViewModel() {

    private val _movieHomePageEvent: Channel<MovieHomePageEvent> = Channel(capacity = Channel.UNLIMITED)
    internal val movieHomePageEvent = _movieHomePageEvent.receiveAsFlow()

    private val _movieBanners: MutableStateFlow<List<MovieBanner>> = MutableStateFlow(listOf())
    internal val movieBannersFlow: StateFlow<List<MovieBanner>>
        get() = _movieBanners

    private val _bannerSliderPage: MutableStateFlow<Int> = MutableStateFlow(0)
    internal val bannerSliderPage: StateFlow<Int>
        get() = _bannerSliderPage

    private val _categoriesFlow: MutableStateFlow<List<MovieCategory>> = MutableStateFlow(listOf())
    internal val categoriesFlow: StateFlow<List<MovieCategory>>
        get() = _categoriesFlow

    private val _popularMoviesFlow: MutableStateFlow<MovieHomeUiState> = MutableStateFlow(
        MovieHomeUiState(isLoading = true)
    )
    internal val popularMoviesFlow: StateFlow<MovieHomeUiState>
        get() = _popularMoviesFlow

    private val _topRatedMoviesFlow: MutableStateFlow<MovieHomeUiState> = MutableStateFlow(MovieHomeUiState(isLoading = true))
    internal val topRatedMoviesFlow: StateFlow<MovieHomeUiState>
        get() = _topRatedMoviesFlow



    internal fun loadMovieBanners() {
        executeUseCase(
            param = Unit,
            useCase = getMovieBannersUseCase,
            isShowLoading = false,
            onSuccess = { movies ->
                _movieBanners.value = movies
            }
        )
    }

    internal fun slideMovieBanner() {
        viewModelScope.launch {
            movieRepository.getMovieBannerSlidePageFlow().collect { page ->
                _bannerSliderPage.value = page
            }
        }
    }

    internal fun loadGenres() {
        executeUseCase(
            param = Unit,
            useCase = getGenresUseCase,
            isShowLoading = false,
            onSuccess = {
                _categoriesFlow.value = it
            }
        )
    }

    internal fun loadPopularMovies(page: Int = 1) {
        executeUseCase(
            param = Pair(page, MovieType.POPULAR),
            useCase = getMoviesUseCase,
            isShowLoading = false,
            onSuccess = {
                _popularMoviesFlow.updateState { currentState ->
                    currentState.copy(
                        items = currentState.items.update(it),
                        isLoading = false
                    )
                }
            }
        )
    }

    internal fun loadTopRatedMovies(page: Int = 1) {
        executeUseCase(
            param = Pair(page, MovieType.TOP_RATED),
            useCase = getMoviesUseCase,
            isShowLoading = false,
            onSuccess = {
                _topRatedMoviesFlow.updateState { currentState ->
                    currentState.copy(
                        items = currentState.items.update(it),
                        isLoading = false
                    )
                }
            }
        )
    }

    internal fun onClickPopularMovieItem(position: Int) {
        val popularMovies = popularMoviesFlow.value.items
        val selectedMovie = popularMovies[position]
        executeUseCase(
            param = selectedMovie.id,
            useCase = getMovieDetailUseCase,
            isShowLoading = true,
            onSuccess = { detailInfo ->
                viewModelScope.launch {
                    _movieHomePageEvent.send(MovieHomePageEvent.MovieDetailNavigation(detailInfo))
                }
            }
        )
    }


}