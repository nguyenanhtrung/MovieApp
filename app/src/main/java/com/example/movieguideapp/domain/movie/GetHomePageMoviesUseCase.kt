package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetHomePageMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Pair<Int, MovieType>, List<MovieHomePageUiData>>() {

    override suspend fun execute(param: Pair<Int, MovieType>): WorkResult<List<MovieHomePageUiData>> {
        return movieRepository.getMovies(param.first, param.second)
    }
}