package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.ItemViewModel

abstract class StickyHeaderDecoration(
    protected val headerPositionProvider: HeaderPositionProvider,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderViewHolder: DataBindingViewHolder? = null

    // Ensure deprecated method is not used
    final override fun onDraw(c: Canvas, parent: RecyclerView) {
    }

    @CallSuper
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        if (parent.layoutManager == null) return
        val adapter: RecyclerView.Adapter<*> = parent.adapter ?: return
        check(adapter is Dabirva) { "Required an instance of Dabirva but was $adapter" }
        val dabirva: Dabirva = adapter
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

        val items: List<ItemViewModel> = dabirva.items
        val headerAdapterPosition: Int = headerPositionProvider.getHeaderPositionForItem(topChildAdapterPosition, items)
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

    // Ensure deprecated method is not used
    final override fun onDrawOver(canvas: Canvas, parent: RecyclerView) {
    }

    // Ensure deprecated method is not used
    final override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
    }

    @CallSuper
    protected open fun onBoundHeaderViewHolder(
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
    ) {
    }

    @CallSuper
    protected open fun onDrawHeaderOverItems(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
    ) {
    }

}
