package com.matbadev.dabirva.decoration

import com.matbadev.dabirva.ItemViewModel

interface HeaderPositionProvider {

    fun getHeaderPositionForItem(itemPosition: Int, items: List<ItemViewModel>): Int

}
