package com.matbadev.dabirva

import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

data class DabirvaData(
    val items: List<ItemViewModel> = listOf(),
    val itemDecorations: List<RecyclerView.ItemDecoration> = listOf(),
    val diffExecutor: Executor? = null,
)
