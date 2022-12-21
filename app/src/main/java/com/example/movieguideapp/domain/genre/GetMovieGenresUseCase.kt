package com.example.movieguideapp.domain.genre

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.repository.genre.GenreRepository
import com.example.movieguideapp.domain.base.BaseUseCase
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(private val genreRepository: GenreRepository) :
    BaseUseCase<Unit, List<MovieCategory>>() {


    override suspend fun execute(param: Unit): WorkResult<List<MovieCategory>> {
        return genreRepository.getMovieGenres()
    }
}