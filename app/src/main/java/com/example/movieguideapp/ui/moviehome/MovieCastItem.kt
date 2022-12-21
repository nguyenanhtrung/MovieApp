package com.example.movieguideapp.ui.moviehome

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.databinding.ItemCastMovieDetailBinding
import com.example.movieguideapp.extensions.loadAsync
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieCastItem(private val movieCastInfo: MovieDetailCastInfo) :
    AbstractBindingItem<ItemCastMovieDetailBinding>() {
    override val type: Int = movieCastInfo.id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCastMovieDetailBinding {
        return ItemCastMovieDetailBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemCastMovieDetailBinding, payloads: List<Any>) {
        with(binding) {
            textCastNameMovieDetail.text = movieCastInfo.castName
            textCharNameMovieDetail.text = movieCastInfo.charName
            imageCastMovieDetail.loadAsync(movieCastInfo.imageUrl)
        }
    }
}