package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

@Suppress("CascadeIf")
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("dabirvaItems")
    fun setItems(recyclerView: RecyclerView, itemViewModels: List<ItemViewModel>) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            recyclerView.adapter = Dabirva(initialItems = itemViewModels)
        } else if (currentAdapter is Dabirva) {
            currentAdapter.items = itemViewModels
        } else {
            throw IllegalStateException("Different adapter already set")
        }
    }

    @JvmStatic
    @BindingAdapter("dabirvaItemDecorations")
    fun setItemDecorations(recyclerView: RecyclerView, itemDecorations: List<RecyclerView.ItemDecoration>) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            recyclerView.adapter = Dabirva(initialItemDecorations = itemDecorations)
        } else if (currentAdapter is Dabirva) {
            currentAdapter.itemDecorations = itemDecorations
        } else {
            throw IllegalStateException("Different adapter already set")
        }
    }

    @JvmStatic
    @BindingAdapter("dabirvaDiffExecutor")
    fun setDiffExecutor(recyclerView: RecyclerView, diffExecutor: Executor?) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            recyclerView.adapter = Dabirva(initialDiffExecutor = diffExecutor)
        } else if (currentAdapter is Dabirva) {
            currentAdapter.diffExecutor = diffExecutor
        } else {
            throw IllegalStateException("Different adapter already set")
        }
    }

}
