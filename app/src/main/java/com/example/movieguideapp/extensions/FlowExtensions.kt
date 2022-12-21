package com.example.movieguideapp.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun<T> MutableStateFlow<T>.updateState(onUpdate: (currentState: T) -> T) {
    val currentState = value
    value = onUpdate(currentState)
}