package com.matbadev.dabirva.internal

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.decoration.DabirvaItemDecoration

internal class DabirvaItemDecorationDelegate(
    private val dabirvaItemDecoration: DabirvaItemDecoration,
) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val dabirva: Dabirva = requireDabirva(parent)
        dabirvaItemDecoration.onDraw(canvas, parent, state, dabirva)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val dabirva: Dabirva = requireDabirva(parent)
        dabirvaItemDecoration.onDrawOver(canvas, parent, state, dabirva)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dabirva: Dabirva = requireDabirva(parent)
        dabirvaItemDecoration.getItemOffsets(outRect, view, parent, state, dabirva)
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
