package com.example.movieguideapp.data.local.model

sealed class ListState<T> {
    object Loading: ListState<Any>()
    class DataLoaded<T>(val items: List<T>): ListState<T>()
}
