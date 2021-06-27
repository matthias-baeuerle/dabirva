package com.matbadev.dabirva

import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

/**
 * Contains all data required by [Dabirva] to build a list.
 *
 * For each [RecyclerView] defined in a XML layout
 * the corresponding view model should provide an instance of this class
 * which should be bound to [Dabirva] using the binding adapter provided by [setDabirvaData].
 *
 * This class exists to be able to define and apply all list data at once
 * instead of having to define multiple fields in the view model for a single list.
 */
data class DabirvaData(
    val items: List<ItemViewModel> = listOf(),
    val itemDecorations: List<RecyclerView.ItemDecoration> = listOf(),
    val diffExecutor: Executor? = null,
)
