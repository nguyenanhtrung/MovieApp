package com.example.movieguideapp.extensions

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.movieguideapp.R

fun RecyclerView.addHorizontalItemSpace() {
    val itemDecoration = DividerItemDecoration(context, LinearLayout.HORIZONTAL)
    itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.linear_space_item_decoration)!!)
    addItemDecoration(itemDecoration)
}

fun RecyclerView.addVerticalItemSpace() {
    val itemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
    itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.vertical_space_item_decoration)!!)
    addItemDecoration(itemDecoration)
}

