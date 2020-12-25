package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import com.matbadev.dabirva.Identifiable

internal class IdentifiablesDiffUtilCallback<T : Identifiable>(
    private val oldList: List<T>,
    private val newList: List<T>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
