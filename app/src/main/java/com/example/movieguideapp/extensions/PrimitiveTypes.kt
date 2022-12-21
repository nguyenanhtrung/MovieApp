package com.example.movieguideapp.extensions

fun String?.getOrEmpty(): String {
    return if (isNullOrEmpty()) "" else this
}