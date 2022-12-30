package com.example.movieguideapp.data.repository.genre

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.remote.base.NetworkBoundResource
import com.example.movieguideapp.data.remote.datasource.genre.GenreRemoteDataSource
import com.example.movieguideapp.extensions.mapTo
import javax.inject.Inject

class GenreRepositoryImp @Inject constructor(private val genreRemoteDataSource: GenreRemoteDataSource): GenreRepository {


    override suspend fun getMovieGenres(): WorkResult<List<MovieCategory>> {
       return object : NetworkBoundResource<Unit, List<MovieCategory>>(){
           override suspend fun fetchFromNetwork(param: Unit): List<MovieCategory> {
               val response = genreRemoteDataSource.getMovieGenres()
               val genres = response.genres ?: listOf()
               return genres.map { it.mapTo() }
           }

           override suspend fun fetchFromLocal(param: Unit): List<MovieCategory> {
               return listOf()
           }


       }.execute(Unit)
    }
}