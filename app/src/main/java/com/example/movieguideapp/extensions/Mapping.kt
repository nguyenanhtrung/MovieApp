package com.example.movieguideapp.extensions

import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.data.remote.response.Cast
import com.example.movieguideapp.data.remote.response.Genre
import com.example.movieguideapp.data.remote.response.Result
import com.example.movieguideapp.ui.moviehome.MovieBannerItem
import com.example.movieguideapp.ui.moviehome.MovieCastItem
import com.example.movieguideapp.ui.moviehome.MovieCategoryItem
import com.example.movieguideapp.ui.moviehome.MovieHomePageItem

fun Result.mapTo(): MovieHomePageUiData {
    return MovieHomePageUiData(
        id = id ?: 0,
        name = originalTitle ?: "",
        imageUrl = posterPath ?: "",
        rating = voteAverage ?: 0.0
    )
}

fun MovieHomePageUiData.mapTo(): MovieHomePageItem {
    return MovieHomePageItem(this)
}

fun Result.mapToMovieBanner(): MovieBanner {
    return MovieBanner(
        id = id ?: 0,
        imageUrl = posterPath ?: ""
    )
}

fun MovieBanner.mapTo(): MovieBannerItem {
    return MovieBannerItem(this)
}

fun Genre.mapTo(): MovieCategory {
    return MovieCategory(
        id ?: 0,
        name ?: ""
    )
}

fun MovieCategory.mapTo(): MovieCategoryItem {
    return MovieCategoryItem(this)
}

fun Cast.mapTo(): MovieDetailCastInfo {
    return MovieDetailCastInfo(
        id = this.id ?: 0,
        imageUrl = profilePath.getOrEmpty(),
        castName = name.getOrEmpty(),
        charName = character.getOrEmpty()
    )
}

fun MovieDetailCastInfo.mapTo(): MovieCastItem {
    return MovieCastItem(this)
}