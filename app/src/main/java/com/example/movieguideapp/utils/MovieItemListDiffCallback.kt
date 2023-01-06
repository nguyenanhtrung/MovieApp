package com.example.movieguideapp.utils

import com.example.movieguideapp.ui.movies.MovieListItem
import com.mikepenz.fastadapter.diff.DiffCallback

class MovieItemListDiffCallback: DiffCallback<MovieListItem> {
    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.movie == newItem.movie
    }

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    override fun getChangePayload(
        oldItem: MovieListItem,
        oldItemPosition: Int,
        newItem: MovieListItem,
        newItemPosition: Int
    ): Any? = null
}