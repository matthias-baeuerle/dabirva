package com.matbadev.dabirva.decoration

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel

class ItemHeaderProvider(
    private val headerPredicate: (item: ItemViewModel) -> Boolean,
) : HeaderPositionProvider {

    override fun getHeaderPositionForItem(itemPosition: Int, items: List<ItemViewModel>): Int {
        for (position in itemPosition downTo 0) {
            if (headerPredicate.invoke(items[position])) {
                return position
            }
        }
        return RecyclerView.NO_POSITION
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemHeaderProvider) return false
        return headerPredicate == other.headerPredicate
    }

    override fun hashCode(): Int {
        return headerPredicate.hashCode()
    }

    override fun toString(): String {
        return "ItemHeaderProvider(headerPredicate=$headerPredicate)"
    }

}
