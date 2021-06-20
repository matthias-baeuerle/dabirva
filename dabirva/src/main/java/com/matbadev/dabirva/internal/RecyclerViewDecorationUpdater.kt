package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

internal object RecyclerViewDecorationUpdater {

    fun updateDecorations(recyclerView: RecyclerView, decorations: List<ItemDecoration>) {
        val existingDecorations: List<ItemDecoration> = collectDecorations(recyclerView)
        if (existingDecorations != decorations) {
            val layoutSuppressed: Boolean = recyclerView.isLayoutSuppressed
            recyclerView.suppressLayout(true)
            existingDecorations.forEach { decoration: ItemDecoration ->
                recyclerView.removeItemDecoration(decoration)
            }
            decorations.forEach { decoration: ItemDecoration ->
                recyclerView.addItemDecoration(decoration)
            }
            recyclerView.suppressLayout(layoutSuppressed)
        }
    }

    private fun collectDecorations(recyclerView: RecyclerView): List<ItemDecoration> {
        val decorations = mutableListOf<ItemDecoration>()
        for (decorationIndex in 0 until recyclerView.itemDecorationCount) {
            decorations += recyclerView.getItemDecorationAt(decorationIndex)
        }
        return decorations
    }

}
