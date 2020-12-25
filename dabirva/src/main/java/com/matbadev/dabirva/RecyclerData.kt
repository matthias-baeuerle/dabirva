package com.matbadev.dabirva

import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

data class RecyclerData(
    val recyclables: List<Recyclable> = listOf(),
    val decorations: List<RecyclerView.ItemDecoration> = listOf(),
    val diffExecutor: Executor? = null,
)
