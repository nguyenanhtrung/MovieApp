package com.example.movieguideapp.extensions

fun<T> List<T>.update(newItems: List<T>): List<T> {
    val currentList = toMutableList()
    currentList.addAll(newItems)
    return currentList
}