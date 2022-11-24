package com.example.movieguideapp.extensions

import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.remote.response.Result

fun Result.mapTo(): MovieHomePageUiData {
    return MovieHomePageUiData(
        id = id ?: 0,
        name = originalTitle ?: "",
        imageUrl = posterPath ?: "",
        rating = voteAverage ?: 0.0
    )
}

fun Result.mapToMovieBanner(): MovieBanner {
    return MovieBanner(
        id = id ?: 0,
        imageUrl = posterPath ?: ""
    )
}