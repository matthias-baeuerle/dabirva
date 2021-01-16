package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("dabirvaData")
fun setDabirvaData(recyclerView: RecyclerView, dabirvaData: DabirvaData?) {
    if (dabirvaData == null) return
    when (val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter) {
        null -> recyclerView.adapter = Dabirva(dabirvaData)
        is Dabirva -> currentAdapter.data = dabirvaData
        else -> throw IllegalStateException("Different adapter already set")
    }
}
