package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

internal class DecorationListUpdateCallback(
    private val recyclerView: RecyclerView,
    private val delegate: DecorationListUpdateCallbackDelegate,
) : ListUpdateCallback {

    override fun onInserted(position: Int, count: Int) {
        for (decorationPosition in position until position + count) {
            val decoration = delegate.getDecoration(decorationPosition)
            recyclerView.addItemDecoration(decoration, decorationPosition)
        }
    }

    override fun onRemoved(position: Int, count: Int) {
        for (decorationPosition in position + count - 1 downTo position) {
            recyclerView.removeItemDecorationAt(decorationPosition)
        }
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        val decoration: ItemDecoration = recyclerView.getItemDecorationAt(fromPosition)
        recyclerView.removeItemDecoration(decoration)
        recyclerView.addItemDecoration(decoration, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        for (decorationPosition in position + count - 1 downTo position) {
            val decoration: ItemDecoration = recyclerView.getItemDecorationAt(decorationPosition)
            recyclerView.removeItemDecoration(decoration)
            recyclerView.addItemDecoration(decoration, decorationPosition)
        }
    }

}
