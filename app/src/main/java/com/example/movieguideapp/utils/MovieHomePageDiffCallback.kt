package com.example.movieguideapp.utils

import com.example.movieguideapp.ui.moviehome.MovieHomePageItem
import com.mikepenz.fastadapter.diff.DiffCallback

class MovieHomePageDiffCallback: DiffCallback<MovieHomePageItem> {

    override fun areContentsTheSame(
        oldItem: MovieHomePageItem,
        newItem: MovieHomePageItem
    ): Boolean {
        return oldItem.movieHomePageUiData == newItem.movieHomePageUiData
    }

    override fun areItemsTheSame(oldItem: MovieHomePageItem, newItem: MovieHomePageItem): Boolean {
        return oldItem.movieHomePageUiData.id == newItem.movieHomePageUiData.id
    }

    override fun getChangePayload(
        oldItem: MovieHomePageItem,
        oldItemPosition: Int,
        newItem: MovieHomePageItem,
        newItemPosition: Int
    ): Any? {
        return null
    }
}