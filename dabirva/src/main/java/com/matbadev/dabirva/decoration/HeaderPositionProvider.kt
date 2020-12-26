package com.matbadev.dabirva.decoration

import com.matbadev.dabirva.Recyclable

interface HeaderPositionProvider {

    fun getHeaderPositionForItem(itemPosition: Int, recyclables: List<Recyclable>): Int

}
