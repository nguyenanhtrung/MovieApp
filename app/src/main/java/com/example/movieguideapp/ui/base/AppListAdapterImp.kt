package com.example.movieguideapp.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.movieguideapp.ui.moviehome.MovieHomePageItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.diff.DiffCallback
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

class AppListAdapterImp<M, I>(
    private val listItems: List<M>,
    private val transform: (model: M) -> I,
    private val diffCallback: DiffCallback<I>? = null
): AppListAdapter<M> where I : GenericItem {

    private val fastAdapter: GenericFastAdapter
    private val itemAdapter: GenericItemAdapter = ItemAdapter()
    private val alternativeAdapter: GenericItemAdapter = ItemAdapter()

    init {
        fastAdapter = FastAdapter.Companion.with(listOf(alternativeAdapter, itemAdapter))
        val uiItems = listItems.map { transform(it) }
        itemAdapter.add(uiItems)
    }

    override fun add(item: M) {
        itemAdapter.add(
            transform(item)
        )
    }

    override fun addAll(items: List<M>) {
        itemAdapter.add(
            items.map { transform(it) }
        )
    }

    override fun setOnItemClickListener(onItemClick: (position: Int) -> Unit) {
        fastAdapter.onClickListener = { view, adapter, item, position ->
            // Handle click here
            onItemClick(position)
            false
        }
    }

    override fun hideLoading() {
        alternativeAdapter.clear()
    }

    override fun set(items: List<M>) {
        val mapItems = items.map {
            transform(it)
        }
        if (diffCallback != null) {
            FastAdapterDiffUtil.set(itemAdapter as ItemAdapter<I>, mapItems, diffCallback, true)
        } else {
            itemAdapter.set(mapItems)
        }
    }

    override fun showLoading() {
        alternativeAdapter.add(ProgressItem())
    }

    override fun getRecyclerViewAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> = fastAdapter

}