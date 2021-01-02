package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva

abstract class DabirvaItemDecoration : RecyclerView.ItemDecoration() {

    @Deprecated("Deprecated RecyclerView API")
    final override fun onDraw(canvas: Canvas, parent: RecyclerView) {
    }

    final override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dabirva: Dabirva = requireDabirva(parent)
        onDraw(canvas, parent, state, dabirva)
    }

    open fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

    @Deprecated("Deprecated RecyclerView API")
    final override fun onDrawOver(canvas: Canvas, parent: RecyclerView) {
    }

    final override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dabirva: Dabirva = requireDabirva(parent)
        onDrawOver(canvas, parent, state, dabirva)
    }

    open fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

    @Deprecated("Deprecated RecyclerView API")
    final override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
    }

    final override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val dabirva: Dabirva = requireDabirva(parent)
        getItemOffsets(outRect, view, parent, state, dabirva)
    }

    open fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
    }

    private fun requireDabirva(recyclerView: RecyclerView): Dabirva {
        val adapter: RecyclerView.Adapter<*> = recyclerView.adapter
            ?: throw AssertionError("No adapter attached when decoration drawing was requested")
        if (adapter !is Dabirva) {
            throw IllegalStateException("Instances of DabirvaItemDecorationDelegate must only be attached to RecyclerViews using Dabirva")
        }
        return adapter
    }

}
