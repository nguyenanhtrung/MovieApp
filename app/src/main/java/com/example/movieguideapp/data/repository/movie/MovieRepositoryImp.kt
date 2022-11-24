package com.example.movieguideapp.data.repository.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.remote.base.NetworkBoundResource
import com.example.movieguideapp.data.remote.datasource.movie.MovieRemoteDataSource
import com.example.movieguideapp.extensions.mapTo
import com.example.movieguideapp.extensions.mapToMovieBanner
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val remoteDataSource: MovieRemoteDataSource): MovieRepository {


    override suspend fun getPopularMovies(page: Int): WorkResult<List<MovieHomePageUiData>> {
        return object : NetworkBoundResource<Int, List<MovieHomePageUiData>>(){
            override suspend fun fetchFromNetwork(param: Int): List<MovieHomePageUiData> {
                val response = remoteDataSource.getPopularMovies(param)
                val movies = response.results ?: listOf()
                return movies.map {
                    it.mapTo()
                }
            }

            override suspend fun fetchFromLocal(param: Int): List<MovieHomePageUiData> {
                return listOf()
            }

        }.execute(page)
    }

    override suspend fun getTrendingMovies(): WorkResult<List<MovieBanner>> {
        return object : NetworkBoundResource<Unit, List<MovieBanner>>() {
            override suspend fun fetchFromNetwork(param: Unit): List<MovieBanner> {
                val response = remoteDataSource.getTrendingMovies()
                val movies = response.results ?: listOf()
                return movies.map { it.mapToMovieBanner() }
            }

            override suspend fun fetchFromLocal(param: Unit): List<MovieBanner> {
                return listOf()
            }

        }.execute(Unit)
    }

}