package com.matbadev.dabirva

import androidx.recyclerview.widget.RecyclerView

enum class LinearOrientation(
    val recyclerViewValue: Int,
) {
    HORIZONTAL(RecyclerView.HORIZONTAL),
    VERTICAL(RecyclerView.VERTICAL),
    ;

    companion object {
        fun fromRecyclerViewValue(@RecyclerView.Orientation orientation: Int): LinearOrientation {
            return when (orientation) {
                RecyclerView.HORIZONTAL -> HORIZONTAL
                RecyclerView.VERTICAL -> VERTICAL
                else -> throw IllegalArgumentException("Invalid RecyclerView orientation value $orientation")
            }
        }
    }
}
