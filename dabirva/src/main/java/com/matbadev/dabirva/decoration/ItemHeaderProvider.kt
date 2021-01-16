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

}
