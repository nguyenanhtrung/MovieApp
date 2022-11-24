package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, List<MovieHomePageUiData>>() {

    override suspend fun execute(param: Int): WorkResult<List<MovieHomePageUiData>> {
        return movieRepository.getPopularMovies(param)
    }
}