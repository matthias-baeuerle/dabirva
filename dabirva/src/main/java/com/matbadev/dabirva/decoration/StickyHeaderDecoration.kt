package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.util.requireDabirva

abstract class StickyHeaderDecoration(
    protected val headerPositionProvider: HeaderPositionProvider,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderViewHolder: DataBindingViewHolder? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) return

        val dabirva: Dabirva = parent.requireDabirva()
        val headerRecyclable: Recyclable? = getHeaderRecyclable(parent, dabirva)
        var headerViewHolder: DataBindingViewHolder? = currentHeaderViewHolder

        if (headerRecyclable != null) { // Header should be shown
            if (headerViewHolder != null) {
                updateHeaderViewHolder(headerViewHolder, headerRecyclable)
            } else {
                headerViewHolder = getOrCreateHeaderViewHolder(parent, dabirva, headerRecyclable.layoutId)
                headerViewHolder.bindItem(headerRecyclable)
                currentHeaderViewHolder = headerViewHolder
                onBoundHeaderViewHolder(parent, state, dabirva, headerViewHolder, headerRecyclable)
            }
            onDrawHeaderOverItems(canvas, parent, state, dabirva, headerViewHolder)
        } else if (headerViewHolder != null) { // Existing header should not be shown
            headerViewHolder.unbind()
            parent.recycledViewPool.putRecycledView(headerViewHolder)
            currentHeaderViewHolder = null
        }
    }

    private fun getHeaderRecyclable(parent: RecyclerView, dabirva: Dabirva): Recyclable? {
        val topChild: View = parent.getChildAt(0) ?: return null

        val topChildAdapterPosition: Int = parent.getChildAdapterPosition(topChild)
        if (topChildAdapterPosition == RecyclerView.NO_POSITION) return null

        val recyclables: List<Recyclable> = dabirva.data.recyclables
        val headerAdapterPosition: Int =
            headerPositionProvider.getHeaderPositionForItem(topChildAdapterPosition, recyclables)
        if (headerAdapterPosition == RecyclerView.NO_POSITION) return null

        return recyclables[headerAdapterPosition]
    }

    private fun updateHeaderViewHolder(headerViewHolder: DataBindingViewHolder, headerRecyclable: Recyclable) {
        val boundHeaderRecyclable: Recyclable? = headerViewHolder.boundRecyclable
        if (boundHeaderRecyclable == null) {
            headerViewHolder.bindItem(headerRecyclable)
        } else if (boundHeaderRecyclable != headerRecyclable) {
            headerViewHolder.unbind()
            headerViewHolder.bindItem(headerRecyclable)
        }
    }

    private fun getOrCreateHeaderViewHolder(
        parent: RecyclerView,
        dabirva: Dabirva,
        headerViewType: Int,
    ): DataBindingViewHolder {
        val cachedHeaderViewHolder: RecyclerView.ViewHolder? = parent.recycledViewPool.getRecycledView(headerViewType)
        return if (cachedHeaderViewHolder != null) {
            cachedHeaderViewHolder as DataBindingViewHolder
        } else {
            dabirva.createViewHolder(parent, headerViewType)
        }
    }

    @CallSuper
    protected open fun onBoundHeaderViewHolder(
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
        headerRecyclable: Recyclable,
    ) {
    }

    protected abstract fun onDrawHeaderOverItems(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
    )

}
