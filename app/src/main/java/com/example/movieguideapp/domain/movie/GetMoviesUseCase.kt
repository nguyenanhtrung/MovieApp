package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<Pair<Int, MovieType>, List<MovieItemData>>() {

    override suspend fun execute(param: Pair<Int, MovieType>): WorkResult<List<MovieItemData>> {
        return movieRepository.getMovies(param)
    }
}