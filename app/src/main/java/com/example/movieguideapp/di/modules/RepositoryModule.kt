package com.example.movieguideapp.di.modules

import com.example.movieguideapp.data.remote.datasource.genre.GenreRemoteDataSource
import com.example.movieguideapp.data.remote.datasource.genre.GenreRemoteDataSourceImp
import com.example.movieguideapp.data.remote.datasource.movie.MovieRemoteDataSource
import com.example.movieguideapp.data.remote.datasource.movie.MovieRemoteDataSourceImp
import com.example.movieguideapp.data.repository.genre.GenreRepository
import com.example.movieguideapp.data.repository.genre.GenreRepositoryImp
import com.example.movieguideapp.data.repository.movie.MovieRepository
import com.example.movieguideapp.data.repository.movie.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSourceImp): MovieRemoteDataSource

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImp: MovieRepositoryImp): MovieRepository

    @Binds
    abstract fun provideGenreRemoteDataSource(genreRemoteDataSourceImp: GenreRemoteDataSourceImp): GenreRemoteDataSource

    @Binds
    abstract fun provideGenreRepository(genreRepositoryImp: GenreRepositoryImp): GenreRepository
}