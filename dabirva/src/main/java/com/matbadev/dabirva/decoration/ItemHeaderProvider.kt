package com.matbadev.dabirva.decoration

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Recyclable

class ItemHeaderProvider(
    private val headerPredicate: (recyclable: Recyclable) -> Boolean,
) : HeaderPositionProvider {

    override fun getHeaderPositionForItem(itemPosition: Int, recyclables: List<Recyclable>): Int {
        for (position in itemPosition downTo 0) {
            if (headerPredicate.invoke(recyclables[position])) {
                return position
            }
        }
        return RecyclerView.NO_POSITION
    }

}
