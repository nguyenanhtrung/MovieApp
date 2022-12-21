package com.example.movieguideapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movieguideapp.databinding.ItemListProgressBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class ProgressItem: AbstractBindingItem<ItemListProgressBinding>() {
    override val type: Int = 999

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemListProgressBinding {
        return ItemListProgressBinding.inflate(inflater, parent, false)
    }
}