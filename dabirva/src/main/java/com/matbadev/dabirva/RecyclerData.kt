package com.matbadev.dabirva

import androidx.recyclerview.widget.RecyclerView

data class RecyclerData(
    val recyclables: List<Recyclable> = listOf(),
    val decorations: List<RecyclerView.ItemDecoration> = listOf(),
)
