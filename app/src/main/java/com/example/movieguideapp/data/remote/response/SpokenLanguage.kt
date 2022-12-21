package com.example.movieguideapp.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1")
    val iso6391: String?,
    @SerialName("name")
    val name: String?
)