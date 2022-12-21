package com.example.movieguideapp.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MovieDetailCastsResponse(
    @SerialName("cast")
    val cast: List<Cast>?,
    @SerialName("crew")
    val crew: List<Crew?>?,
    @SerialName("id")
    val id: Int?
)