package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetMovieCastsUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, List<MovieDetailCastInfo>>() {

    override suspend fun execute(param: Int): WorkResult<List<MovieDetailCastInfo>> {
        return movieRepository.getMovieCasts(param)
    }
}