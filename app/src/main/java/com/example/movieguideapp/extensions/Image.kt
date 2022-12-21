package com.example.movieguideapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movieguideapp.R

fun ImageView.loadAsync(url: String) {
    Glide.with(this)
        .load("https://image.tmdb.org/t/p/original/$url")
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .into(this)
}