package com.matbadev.dabirva.decoration

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Recyclable

abstract class ItemHeaderProvider : HeaderPositionProvider {

    final override fun getHeaderPositionForItem(itemPosition: Int, recyclables: List<Recyclable>): Int {
        for (position in itemPosition downTo 0) {
            if (isHeader(recyclables[position])) {
                return position
            }
        }
        return RecyclerView.NO_POSITION
    }

    abstract fun isHeader(recyclable: Recyclable): Boolean

}
