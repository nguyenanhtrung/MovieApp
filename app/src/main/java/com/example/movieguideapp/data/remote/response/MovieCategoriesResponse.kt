package com.example.movieguideapp.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MovieCategoriesResponse(
    @SerialName("genres")
    val genres: List<Genre>?
)