package com.example.movieguideapp.data.repository.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieDetailBasicInfo
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.local.model.movie.SimilarMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(page: Int, movieType: MovieType): WorkResult<List<MovieHomePageUiData>>

    suspend fun getTrendingMovies(): WorkResult<List<MovieBanner>>

    suspend fun getMovieDetail(id: Int): WorkResult<MovieDetailBasicInfo>

    suspend fun getMovieCasts(id: Int): WorkResult<List<MovieDetailCastInfo>>

    fun getMovieDetailInfo(movieDetailBasicInfo: MovieDetailBasicInfo): List<Pair<String, String>>

    suspend fun getSimilarMovies(id: Int): WorkResult<List<SimilarMovie>>
}