package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("recyclerData")
fun setRecyclerData(recyclerView: RecyclerView, recyclerData: RecyclerData?) {
    if (recyclerData == null) return
    when (val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter) {
        null -> recyclerView.adapter = Dabirva(recyclerData)
        is Dabirva -> currentAdapter.recyclerData = recyclerData
        else -> throw IllegalStateException("Different adapter already set")
    }
}
