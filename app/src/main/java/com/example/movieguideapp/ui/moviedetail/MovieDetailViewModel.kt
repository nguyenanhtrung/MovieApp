package com.example.movieguideapp.ui.moviedetail

import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.local.model.movie.MovieDetailBasicInfo
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.movie.GetMovieCastsUseCase
import com.example.movieguideapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getMovieCastsUseCase: GetMovieCastsUseCase,
                                            private val movieRepository: MovieRepository): BaseViewModel() {

    private val _basicInfoState: MutableStateFlow<MovieDetailBasicInfo> = MutableStateFlow(MovieDetailBasicInfo())
    internal val basicInfoState: StateFlow<MovieDetailBasicInfo>
        get() = _basicInfoState

    private val _castsState: MutableStateFlow<List<MovieDetailCastInfo>> = MutableStateFlow(listOf())
    internal val castsFlow: StateFlow<List<MovieDetailCastInfo>>
        get() = _castsState

    private val _detailInfoState: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )
    internal val detailInfoState: StateFlow<List<Pair<String,String>>>
        get() = _detailInfoState



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
}