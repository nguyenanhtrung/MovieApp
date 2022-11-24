package com.example.movieguideapp.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Result>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)