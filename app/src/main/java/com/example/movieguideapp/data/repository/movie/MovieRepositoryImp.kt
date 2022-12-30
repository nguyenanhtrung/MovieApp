package com.example.movieguideapp.data.repository.movie

import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieDetailBasicInfo
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.data.local.model.movie.SimilarMovie
import com.example.movieguideapp.data.remote.base.NetworkBoundResource
import com.example.movieguideapp.data.remote.datasource.movie.MovieRemoteDataSource
import com.example.movieguideapp.extensions.getOrEmpty
import com.example.movieguideapp.extensions.mapTo
import com.example.movieguideapp.extensions.mapToMovieBanner
import com.example.movieguideapp.utils.DateTimeUtils
import com.example.movieguideapp.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MovieRepositoryImp @Inject constructor(private val remoteDataSource: MovieRemoteDataSource) :
    MovieRepository {

    override suspend fun getMovies(
        page: Int,
        movieType: MovieType
    ): WorkResult<List<MovieHomePageUiData>> {
        return when (movieType) {
            MovieType.POPULAR -> getPopularMovies(page)
            MovieType.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    override suspend fun getMovies(param: Pair<Int, MovieType>): WorkResult<List<MovieItemData>> {
        return object : NetworkBoundResource<Pair<Int, MovieType>, List<MovieItemData>>() {
            override suspend fun fetchFromNetwork(param: Pair<Int, MovieType>): List<MovieItemData> {
                val response = when(param.second) {
                    MovieType.POPULAR -> {
                        remoteDataSource.getPopularMovies(param.first)
                    }
                    MovieType.TOP_RATED -> {
                        remoteDataSource.getPopularMovies(param.first)
                    }
                }
                val movies = response.results ?: listOf()
                return movies.map {
                    it.mapTo("")
                }
            }

            override suspend fun fetchFromLocal(param: Pair<Int, MovieType>): List<MovieItemData> {
                return listOf()
            }

        }.execute(param)
    }


    private suspend fun getPopularMovies(page: Int): WorkResult<List<MovieHomePageUiData>> {
        return object : NetworkBoundResource<Int, List<MovieHomePageUiData>>() {
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

    private suspend fun getTopRatedMovies(page: Int): WorkResult<List<MovieHomePageUiData>> {
        return object : NetworkBoundResource<Int, List<MovieHomePageUiData>>() {
            override suspend fun fetchFromNetwork(param: Int): List<MovieHomePageUiData> {
                val response = remoteDataSource.getTopRatedMovies(param)
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
                return movies.take(4).map { it.mapToMovieBanner() }
            }

            override suspend fun fetchFromLocal(param: Unit): List<MovieBanner> {
                return listOf()
            }

        }.execute(Unit)
    }

    override suspend fun getMovieDetail(id: Int): WorkResult<MovieDetailBasicInfo> {
        return object : NetworkBoundResource<Int, MovieDetailBasicInfo>() {
            override suspend fun fetchFromNetwork(param: Int): MovieDetailBasicInfo {
                val response = remoteDataSource.getMovieDetail(param)
                val genres = response.genres ?: listOf()
                return MovieDetailBasicInfo(
                    id = response.id ?: -1,
                    name = response.title.getOrEmpty(),
                    backgroundImageUrl = response.backdropPath.getOrEmpty(),
                    posterImageUrl = response.posterPath.getOrEmpty(),
                    category = genres.joinToString(separator = " | ", transform = {
                        it.name.getOrEmpty()
                    }),
                    rating = response.voteAverage ?: 0.0,
                    language = response.originalLanguage.getOrEmpty(),
                    releaseDate = DateTimeUtils.getYear(
                        response.releaseDate.getOrEmpty(),
                        "yyyy-MM-dd"
                    ),
                    overview = response.overview.getOrEmpty(),
                    revenue = response.revenue ?: 0,
                    runtime = response.runtime ?: 0,
                    budge = response.budget ?: 0
                )
            }

            override suspend fun fetchFromLocal(param: Int): MovieDetailBasicInfo {
                return MovieDetailBasicInfo()
            }

        }.execute(id)
    }

    override suspend fun getMovieCasts(id: Int): WorkResult<List<MovieDetailCastInfo>> {
        return object : NetworkBoundResource<Int, List<MovieDetailCastInfo>>() {
            override suspend fun fetchFromNetwork(param: Int): List<MovieDetailCastInfo> {
                val response = remoteDataSource.getMovieCasts(param)
                val casts = response.cast ?: listOf()
                return casts.map {
                    it.mapTo()
                }
            }

            override suspend fun fetchFromLocal(param: Int): List<MovieDetailCastInfo> {
                return listOf()
            }

        }.execute(id)
    }

    override fun getMovieDetailInfo(movieDetailBasicInfo: MovieDetailBasicInfo): List<Pair<String, String>> {
        return listOf(
            Pair("Origin title", movieDetailBasicInfo.name),
            Pair("Budgets", movieDetailBasicInfo.budge.toString()),
            Pair("Runtime", movieDetailBasicInfo.runtime.toString()),
            Pair("Origin title", movieDetailBasicInfo.name),
            Pair("Budgets", movieDetailBasicInfo.budge.toString()),
            Pair("Runtime", movieDetailBasicInfo.runtime.toString()),
            Pair("Origin title", movieDetailBasicInfo.name),
            Pair("Budgets", movieDetailBasicInfo.budge.toString()),
            Pair("Runtime", movieDetailBasicInfo.runtime.toString()),
        )
    }

    override suspend fun getSimilarMovies(id: Int): WorkResult<List<SimilarMovie>> {
        return object : NetworkBoundResource<Int, List<SimilarMovie>>() {
            override suspend fun fetchFromNetwork(param: Int): List<SimilarMovie> {
                val response = remoteDataSource.getSimilarMovies(param)
                val movies = response.results ?: listOf()
                return movies.map {
                    SimilarMovie(id = it.id ?: 0, imageUrl = it.posterPath.getOrEmpty())
                }
            }

            override suspend fun fetchFromLocal(param: Int): List<SimilarMovie> {
                return listOf()
            }

        }.execute(id)
    }

}