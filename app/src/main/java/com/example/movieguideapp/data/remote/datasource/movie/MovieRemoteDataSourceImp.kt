package com.example.movieguideapp.data.remote.datasource.movie

import com.example.movieguideapp.data.remote.api.MovieApiService
import com.example.movieguideapp.data.remote.response.MoviesResponse
import javax.inject.Inject

class MovieRemoteDataSourceImp @Inject constructor(private val movieApiService: MovieApiService): MovieRemoteDataSource {


    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        return movieApiService.getPopularMovies(page)
    }

    override suspend fun getTrendingMovies(): MoviesResponse {
        return movieApiService.getTrendingMovies()
    }
}