package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

internal fun RecyclerView.collectDecorations(): List<ItemDecoration> {
    val decorations = mutableListOf<ItemDecoration>()
    for (decorationIndex in 0 until itemDecorationCount) {
        decorations += getItemDecorationAt(decorationIndex)
    }
    return decorations
}
