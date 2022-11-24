package com.example.movieguideapp.data.remote.datasource.movie

import com.example.movieguideapp.data.remote.response.MoviesResponse

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getTrendingMovies(): MoviesResponse
}