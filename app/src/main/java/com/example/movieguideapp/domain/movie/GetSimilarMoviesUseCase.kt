package com.example.movieguideapp.domain.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.SimilarMovie
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val repository: MovieRepository) :
    BaseUseCase<Int, List<SimilarMovie>>() {
    override suspend fun execute(param: Int): WorkResult<List<SimilarMovie>> {
        return repository.getSimilarMovies(param)
    }
}