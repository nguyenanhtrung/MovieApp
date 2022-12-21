package com.example.movieguideapp.data.repository.genre

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.genre.MovieCategory

interface GenreRepository {
    suspend fun getMovieGenres(): WorkResult<List<MovieCategory>>
}