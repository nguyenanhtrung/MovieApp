package com.example.movieguideapp.data.remote.api

import com.example.movieguideapp.data.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MoviesResponse
}