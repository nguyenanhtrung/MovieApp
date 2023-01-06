package com.example.movieguideapp.data.remote.api

import com.example.movieguideapp.data.remote.response.MovieCategoriesResponse
import com.example.movieguideapp.data.remote.response.MovieDetailCastsResponse
import com.example.movieguideapp.data.remote.response.MovieDetailResponse
import com.example.movieguideapp.data.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getMovieCategories(): MovieCategoriesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Int): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCasts(@Path("movie_id") id: Int): MovieDetailCastsResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") id: Int): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): MoviesResponse
}