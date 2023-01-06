package com.example.movieguideapp.data.remote.datasource.movie

import com.example.movieguideapp.data.remote.api.MovieApiService
import com.example.movieguideapp.data.remote.response.MovieDetailCastsResponse
import com.example.movieguideapp.data.remote.response.MovieDetailResponse
import com.example.movieguideapp.data.remote.response.MoviesResponse
import javax.inject.Inject

class MovieRemoteDataSourceImp @Inject constructor(private val movieApiService: MovieApiService): MovieRemoteDataSource {


    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        return movieApiService.getPopularMovies(page)
    }

    override suspend fun getTrendingMovies(): MoviesResponse {
        return movieApiService.getTrendingMovies()
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesResponse {
        return movieApiService.getTopRatedMovies(page)
    }

    override suspend fun getMovieDetail(id: Int): MovieDetailResponse {
        return movieApiService.getMovieDetail(id)
    }

    override suspend fun getMovieCasts(id: Int): MovieDetailCastsResponse {
        return movieApiService.getMovieCasts(id)
    }

    override suspend fun getSimilarMovies(id: Int): MoviesResponse {
        return movieApiService.getSimilarMovies(id)
    }

    override suspend fun searchMovies(query: String): MoviesResponse {
        return movieApiService.searchMovies(query)
    }
}