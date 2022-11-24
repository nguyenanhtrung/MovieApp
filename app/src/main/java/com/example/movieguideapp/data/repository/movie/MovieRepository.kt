package com.example.movieguideapp.data.repository.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): WorkResult<List<MovieHomePageUiData>>

    suspend fun getTrendingMovies(): WorkResult<List<MovieBanner>>
}