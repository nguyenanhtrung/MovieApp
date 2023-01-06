package com.example.movieguideapp.ui.base

import androidx.recyclerview.widget.RecyclerView

interface AppListAdapter<T> {
    fun add(item: T)

    fun addAll(items: List<T>)

    fun set(items: List<T>)

    fun setOnItemClickListener(onItemClick: (position: Int) -> Unit)

    fun showLoading()

    fun hideLoading()

    fun getRecyclerViewAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    fun filter(query: String)
}