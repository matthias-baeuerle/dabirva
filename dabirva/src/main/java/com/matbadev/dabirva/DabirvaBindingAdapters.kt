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
            val dabirva = Dabirva()
            dabirva.items = items
            recyclerView.adapter = dabirva
        } else {
            check(currentAdapter is Dabirva) { "Required an instance of Dabirva but was $currentAdapter" }
            currentAdapter.items = items
        }
    }

    @JvmStatic
    @BindingAdapter("dabirvaDiffExecutor")
    fun setDiffExecutor(recyclerView: RecyclerView, diffExecutor: Executor?) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            val dabirva = Dabirva()
            dabirva.diffExecutor = diffExecutor
            recyclerView.adapter = dabirva
        } else {
            check(currentAdapter is Dabirva) { "Required an instance of Dabirva but was $currentAdapter" }
            currentAdapter.diffExecutor = diffExecutor
        }
    }

}
