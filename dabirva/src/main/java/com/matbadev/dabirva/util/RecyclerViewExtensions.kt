package com.matbadev.dabirva.util

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva

fun RecyclerView.requireDabirva(): Dabirva {
    val currentAdapter: RecyclerView.Adapter<*> = adapter
        ?: throw IllegalStateException("No adapter attached")
    if (currentAdapter !is Dabirva) {
        throw IllegalStateException("Expected Dabirva but received $currentAdapter")
    }
    return currentAdapter
}
