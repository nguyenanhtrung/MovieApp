package com.example.movieguideapp.data.repository.genre

import android.util.Log
import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.remote.base.NetworkBoundResource
import com.example.movieguideapp.data.remote.datasource.genre.GenreRemoteDataSource
import com.example.movieguideapp.extensions.mapTo
import okhttp3.internal.toImmutableList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepositoryImp @Inject constructor(private val genreRemoteDataSource: GenreRemoteDataSource) :
    GenreRepository {

    private var movieGenres: List<MovieCategory> = listOf()


    override suspend fun getMovieGenres(): WorkResult<List<MovieCategory>> {
        return object : NetworkBoundResource<Unit, List<MovieCategory>>() {
            override suspend fun fetchFromNetwork(param: Unit): List<MovieCategory> {
                val response = genreRemoteDataSource.getMovieGenres()
                val genres = response.genres ?: listOf()
                movieGenres = genres.map { it.mapTo() }
                return movieGenres.toImmutableList()
            }

            override suspend fun fetchFromLocal(param: Unit): List<MovieCategory> {
                return listOf()
            }


        }.execute(Unit)
    }

    override fun getMovieGenresName(genreIds: List<Int>): String {
        return when (movieGenres.isEmpty()) {
            true -> ""
            else -> {
                genreIds.joinToString(" | ") { id ->
                    val genre = movieGenres.firstOrNull { it.id == id }
                    genre?.name ?: ""
                }
            }
        }
    }
}