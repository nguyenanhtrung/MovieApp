package com.example.movieguideapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.movie.SimilarMovie
import com.example.movieguideapp.databinding.ItemSimilarMovieBinding
import com.example.movieguideapp.extensions.loadAsync
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class SimilarMovieItem(private val movie: SimilarMovie): AbstractBindingItem<ItemSimilarMovieBinding>() {


    override val type: Int = movie.id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemSimilarMovieBinding {
       return ItemSimilarMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemSimilarMovieBinding, payloads: List<Any>) {
        with(binding) {
            imageSimilarMovie.loadAsync(movie.imageUrl)
        }
    }
}