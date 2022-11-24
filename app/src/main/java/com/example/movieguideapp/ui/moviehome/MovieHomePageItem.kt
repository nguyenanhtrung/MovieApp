package com.example.movieguideapp.ui.moviehome

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.databinding.ItemHomePageMovieBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieHomePageItem(private val movieHomePageUiData: MovieHomePageUiData) :
    AbstractBindingItem<ItemHomePageMovieBinding>() {


    override val type: Int = movieHomePageUiData.id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemHomePageMovieBinding {
        return ItemHomePageMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemHomePageMovieBinding, payloads: List<Any>) {
        binding.textHomePageMovieName.text = movieHomePageUiData.name
        val rating = movieHomePageUiData.rating
        binding.ratingBarHomePageMovie.rating =
            if (rating > 5) (rating - 5).toFloat() else rating.toFloat()
    }
}