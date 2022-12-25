package com.example.movieguideapp.ui.moviedetail

import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.local.model.movie.MovieDetailBasicInfo
import com.example.movieguideapp.data.local.model.movie.SimilarMovie
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.movie.GetMovieCastsUseCase
import com.example.movieguideapp.domain.movie.GetSimilarMoviesUseCase
import com.example.movieguideapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieCastsUseCase: GetMovieCastsUseCase,
    private val movieRepository: MovieRepository,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) : BaseViewModel() {

    private val _basicInfoState: MutableStateFlow<MovieDetailBasicInfo> =
        MutableStateFlow(MovieDetailBasicInfo())
    internal val basicInfoState: StateFlow<MovieDetailBasicInfo>
        get() = _basicInfoState

    private val _castsState: MutableStateFlow<List<MovieDetailCastInfo>> =
        MutableStateFlow(listOf())
    internal val castsFlow: StateFlow<List<MovieDetailCastInfo>>
        get() = _castsState

    private val _detailInfoState: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )
    internal val detailInfoState: StateFlow<List<Pair<String, String>>>
        get() = _detailInfoState

    private val _similarMoviesState: MutableStateFlow<List<SimilarMovie>> = MutableStateFlow(listOf())
    internal val similarMoviesFlow: StateFlow<List<SimilarMovie>>
        get() = _similarMoviesState


    internal fun initBasicInfoState(movieDetailBasicInfo: MovieDetailBasicInfo) {
        _basicInfoState.value = movieDetailBasicInfo
    }

    internal fun loadCasts() {
        val basicInfo = basicInfoState.value

        executeUseCase(
            param = basicInfo.id,
            useCase = getMovieCastsUseCase,
            isShowLoading = false,
            onSuccess = {
                _castsState.value = it
            }
        )
    }

    internal fun loadDetailInfo() {
        val detailInfo = movieRepository.getMovieDetailInfo(basicInfoState.value)
        _detailInfoState.value = detailInfo
    }

    internal fun loadSimilarMovies() {
        val id = basicInfoState.value.id
        executeUseCase(
            param = id,
            useCase = getSimilarMoviesUseCase,
            isShowLoading = false,
            onSuccess = {
                _similarMoviesState.value = it
            }
        )
    }
}