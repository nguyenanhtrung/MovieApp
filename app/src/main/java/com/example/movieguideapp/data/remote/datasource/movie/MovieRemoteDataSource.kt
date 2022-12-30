package com.example.movieguideapp.data.remote.datasource.movie

import com.example.movieguideapp.data.remote.response.MovieDetailCastsResponse
import com.example.movieguideapp.data.remote.response.MovieDetailResponse
import com.example.movieguideapp.data.remote.response.MoviesResponse

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getTopRatedMovies(page: Int): MoviesResponse

    suspend fun getTrendingMovies(): MoviesResponse

    suspend fun getMovieDetail(id: Int): MovieDetailResponse

    suspend fun getMovieCasts(id: Int): MovieDetailCastsResponse

    suspend fun getSimilarMovies(id: Int): MoviesResponse

}