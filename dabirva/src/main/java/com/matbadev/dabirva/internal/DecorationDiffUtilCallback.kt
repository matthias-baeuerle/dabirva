package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal class DecorationDiffUtilCallback(
    private val oldDecorations: List<RecyclerView.ItemDecoration>,
    private val newDecorations: List<RecyclerView.ItemDecoration>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldDecorations.size
    }

    override fun getNewListSize(): Int {
        return newDecorations.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDecorations[oldItemPosition] == newDecorations[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}
