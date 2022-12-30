package com.example.movieguideapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.databinding.ItemMovieBinding
import com.example.movieguideapp.extensions.loadAsync
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieListItem(private val movie: MovieItemData) : AbstractBindingItem<ItemMovieBinding>() {
    override val type: Int = movie.id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMovieBinding {
        return ItemMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        with(binding) {
            textMovieName.text = movie.name
            textCategoryItemMovie.text = movie.category
            ratingItemMovie.rating = movie.rating.toFloat()
            imageItemMovie.loadAsync(movie.imageUrl)
        }
    }

}