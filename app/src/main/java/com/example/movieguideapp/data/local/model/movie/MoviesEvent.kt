package com.example.movieguideapp.data.local.model.movie

import com.example.movieguideapp.data.local.model.ViewModelEvent

sealed class MoviesEvent {
    class MovieDetailNavigation(val movieDetailBasicInfo: MovieDetailBasicInfo): MoviesEvent()
}