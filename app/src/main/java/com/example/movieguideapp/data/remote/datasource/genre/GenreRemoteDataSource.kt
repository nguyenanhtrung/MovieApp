package com.example.movieguideapp.data.remote.datasource.genre

import com.example.movieguideapp.data.remote.response.MovieCategoriesResponse

interface GenreRemoteDataSource {

    suspend fun getMovieGenres(): MovieCategoriesResponse
}