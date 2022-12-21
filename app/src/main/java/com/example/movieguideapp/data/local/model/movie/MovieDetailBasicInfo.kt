package com.example.movieguideapp.data.local.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailBasicInfo(
    val id: Int = 0,
    val name: String = "",
    val backgroundImageUrl: String = "",
    val posterImageUrl: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val language: String = "",
    val releaseDate: String = "",
    val overview: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val budge: Int = 0
): Parcelable