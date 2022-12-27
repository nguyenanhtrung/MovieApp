package com.example.movieguideapp.data.local.model.movie

sealed class MovieHomePageEvent {
    class MovieDetailNavigation(val movieDetailBasicInfo: MovieDetailBasicInfo): MovieHomePageEvent()
    object MovieBannerSlide: MovieHomePageEvent()
}