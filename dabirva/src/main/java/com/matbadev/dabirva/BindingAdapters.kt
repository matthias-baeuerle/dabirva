package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("dabirvaData")
@Suppress("CascadeIf")
fun setDabirvaData(recyclerView: RecyclerView, dabirvaData: DabirvaData) {
    val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
    if (currentAdapter == null) {
        recyclerView.adapter = Dabirva(dabirvaData)
    } else if (currentAdapter is Dabirva) {
        currentAdapter.data = dabirvaData
    } else {
        throw IllegalStateException("Different adapter already set")
    }
}
