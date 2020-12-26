package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva

abstract class DabirvaItemDecoration {

    open fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

    open fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

    open fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

}
