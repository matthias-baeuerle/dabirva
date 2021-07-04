package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.internal.RecyclerViewDecorationUpdater

object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("itemDecorations")
    fun setItemDecorations(recyclerView: RecyclerView, itemDecorations: List<RecyclerView.ItemDecoration>) {
        RecyclerViewDecorationUpdater.updateDecorations(recyclerView, itemDecorations)
    }

}
