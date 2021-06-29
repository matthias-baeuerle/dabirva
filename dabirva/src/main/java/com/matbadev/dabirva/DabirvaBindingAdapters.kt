package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

@Suppress("CascadeIf")
object DabirvaBindingAdapters {

    @JvmStatic
    @BindingAdapter("dabirvaItems")
    fun setItems(recyclerView: RecyclerView, items: List<ItemViewModel>) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            val dabirva: Dabirva = DabirvaConfig.factory.create()
            dabirva.items = items
            recyclerView.adapter = dabirva
        } else if (currentAdapter is Dabirva) {
            currentAdapter.items = items
        } else {
            throw IllegalStateException("Different adapter already set")
        }
    }

    @JvmStatic
    @BindingAdapter("dabirvaDiffExecutor")
    fun setDiffExecutor(recyclerView: RecyclerView, diffExecutor: Executor?) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            val dabirva: Dabirva = DabirvaConfig.factory.create()
            dabirva.diffExecutor = diffExecutor
            recyclerView.adapter = dabirva
        } else if (currentAdapter is Dabirva) {
            currentAdapter.diffExecutor = diffExecutor
        } else {
            throw IllegalStateException("Different adapter already set")
        }
    }

}
