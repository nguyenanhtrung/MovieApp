package com.example.movieguideapp.domain.moviedetail

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieDetailBasicInfo
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, MovieDetailBasicInfo>() {

    override suspend fun execute(param: Int): WorkResult<MovieDetailBasicInfo> {
        return movieRepository.getMovieDetail(param)
    }
}