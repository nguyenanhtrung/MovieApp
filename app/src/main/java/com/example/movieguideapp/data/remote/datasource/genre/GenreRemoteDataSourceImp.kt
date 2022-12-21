package com.example.movieguideapp.data.remote.datasource.genre

import com.example.movieguideapp.data.remote.api.MovieApiService
import com.example.movieguideapp.data.remote.response.MovieCategoriesResponse
import javax.inject.Inject

class GenreRemoteDataSourceImp @Inject constructor(private val movieApiService: MovieApiService) :
    GenreRemoteDataSource {


    override suspend fun getMovieGenres(): MovieCategoriesResponse {
        return movieApiService.getMovieCategories()
    }
}