package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.RecyclerView.ItemDecoration

internal interface DecorationListUpdateCallbackDelegate {

    fun getDecoration(newPosition: Int): ItemDecoration

}
