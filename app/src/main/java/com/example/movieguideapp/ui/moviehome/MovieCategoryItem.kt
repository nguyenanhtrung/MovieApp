package com.example.movieguideapp.ui.moviehome

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.databinding.ItemCategoryBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MovieCategoryItem(private val movieCategory: MovieCategory): AbstractBindingItem<ItemCategoryBinding>() {

    override val type: Int = movieCategory.id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemCategoryBinding {
        return ItemCategoryBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemCategoryBinding, payloads: List<Any>) {
        binding.textCategoryName.text = movieCategory.name
    }
}