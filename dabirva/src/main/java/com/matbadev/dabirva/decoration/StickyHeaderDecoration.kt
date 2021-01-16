package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.util.requireDabirva

abstract class StickyHeaderDecoration(
    protected val headerPositionProvider: HeaderPositionProvider,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderViewHolder: DataBindingViewHolder? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) return

        val dabirva: Dabirva = parent.requireDabirva()
        val headerViewModel: ItemViewModel? = getHeaderViewModel(parent, dabirva)
        var headerViewHolder: DataBindingViewHolder? = currentHeaderViewHolder

        if (headerViewModel != null) { // Header should be shown
            if (headerViewHolder != null) {
                updateHeaderViewHolder(headerViewHolder, headerViewModel)
            } else {
                headerViewHolder = getOrCreateHeaderViewHolder(parent, dabirva, headerViewModel.layoutId)
                headerViewHolder.bindViewModel(headerViewModel)
                currentHeaderViewHolder = headerViewHolder
                onBoundHeaderViewHolder(parent, state, dabirva, headerViewHolder)
            }
            onDrawHeaderOverItems(canvas, parent, state, dabirva, headerViewHolder)
        } else if (headerViewHolder != null) { // Existing header should not be shown
            headerViewHolder.unbind()
            parent.recycledViewPool.putRecycledView(headerViewHolder)
            currentHeaderViewHolder = null
        }
    }

    private fun getHeaderViewModel(parent: RecyclerView, dabirva: Dabirva): ItemViewModel? {
        val topChild: View = parent.getChildAt(0) ?: return null

        val topChildAdapterPosition: Int = parent.getChildAdapterPosition(topChild)
        if (topChildAdapterPosition == RecyclerView.NO_POSITION) return null

        val items: List<ItemViewModel> = dabirva.data.items
        val headerAdapterPosition: Int =
            headerPositionProvider.getHeaderPositionForItem(topChildAdapterPosition, items)
        if (headerAdapterPosition == RecyclerView.NO_POSITION) return null

        return items[headerAdapterPosition]
    }

    private fun updateHeaderViewHolder(headerViewHolder: DataBindingViewHolder, headerViewModel: ItemViewModel) {
        val boundHeaderItemViewModel: ItemViewModel? = headerViewHolder.boundViewModel
        if (boundHeaderItemViewModel == null) {
            headerViewHolder.bindViewModel(headerViewModel)
        } else if (boundHeaderItemViewModel != headerViewModel) {
            headerViewHolder.unbind()
            headerViewHolder.bindViewModel(headerViewModel)
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
