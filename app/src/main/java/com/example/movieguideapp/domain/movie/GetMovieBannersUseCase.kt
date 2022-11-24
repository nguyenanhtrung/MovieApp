package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetMovieBannersUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Unit, List<MovieBanner>>() {
    override suspend fun execute(param: Unit): WorkResult<List<MovieBanner>> {
        return movieRepository.getTrendingMovies()
    }
}