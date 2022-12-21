package com.example.movieguideapp.ui.moviehome

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.databinding.ItemMovieBannerBinding
import com.example.movieguideapp.extensions.loadAsync
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieBannerItem(private val movieBanner: MovieBanner) :
    AbstractBindingItem<ItemMovieBannerBinding>() {


    override val type: Int = movieBanner.id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemMovieBannerBinding {
        return ItemMovieBannerBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieBannerBinding, payloads: List<Any>) {
        binding.imageMovieBanner.loadAsync(movieBanner.imageUrl)
    }
}