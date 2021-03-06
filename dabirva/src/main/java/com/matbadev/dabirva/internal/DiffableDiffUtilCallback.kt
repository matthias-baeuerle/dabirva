package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import com.matbadev.dabirva.Diffable

internal class DiffableDiffUtilCallback(
    private val oldList: List<Diffable>,
    private val newList: List<Diffable>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].entityEquals(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
