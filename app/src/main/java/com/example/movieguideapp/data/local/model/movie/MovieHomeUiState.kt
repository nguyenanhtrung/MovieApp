package com.example.movieguideapp.data.local.model.movie

data class MovieHomeUiState(
    val items: List<MovieHomePageUiData> = listOf(),
    val isLoading: Boolean
)