package com.example.movieguideapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.databinding.ItemInfoMovieDetailBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieDetailInfoItem(private val pair: Pair<String, String>): AbstractBindingItem<ItemInfoMovieDetailBinding>() {
    override val type: Int
        get() = pair.second.hashCode()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemInfoMovieDetailBinding {
        return ItemInfoMovieDetailBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemInfoMovieDetailBinding, payloads: List<Any>) {
        with(binding) {
            titleInfoMovieDetail.text = pair.first
            textContentInfoMovieDetail.text = pair.second
        }
    }
}