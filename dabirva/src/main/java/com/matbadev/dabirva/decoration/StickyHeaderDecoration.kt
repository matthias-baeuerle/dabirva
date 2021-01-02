package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.Recyclable

class StickyHeaderDecoration(
    private val headerPositionProvider: HeaderPositionProvider,
) : DabirvaItemDecoration() {

    private var currentHeaderViewHolder: DataBindingViewHolder? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State, dabirva: Dabirva) {
        val layoutManager: RecyclerView.LayoutManager = parent.layoutManager ?: return
        // TODO: Check for GridLayoutManager which extends LinearLayoutManager
        if (layoutManager !is LinearLayoutManager) {
            throw UnsupportedOperationException("Only LinearLayoutManager is supported")
        }

        val recyclables: List<Recyclable> = dabirva.recyclerData.recyclables
        val headerRecyclable: Recyclable? = getHeaderRecyclable(parent, recyclables)
        var headerViewHolder: DataBindingViewHolder? = currentHeaderViewHolder

        if (headerRecyclable != null) { // Header should be shown
            if (headerViewHolder != null) {
                updateHeaderViewHolder(headerViewHolder, headerRecyclable)
            } else {
                headerViewHolder = getOrCreateHeaderViewHolder(parent, dabirva, headerRecyclable.layoutId)
                makeViewSticky(parent, headerViewHolder.itemView)
                headerViewHolder.bindItem(headerRecyclable)
                currentHeaderViewHolder = headerViewHolder
            }

            val headerView: View = headerViewHolder.itemView

            val contactPoint: Int = headerView.bottom
            val childInContact: View? = getChildInContact(parent, contactPoint)

            if (childInContact != null && isItemSticky(parent, recyclables, childInContact)) {
                moveHeader(canvas, headerView, childInContact)
            } else {
                drawHeader(canvas, headerView)
            }
        } else if (headerViewHolder != null) { // Existing header should not be shown
            headerViewHolder.unbind()
            parent.recycledViewPool.putRecycledView(headerViewHolder)
            currentHeaderViewHolder = null
        }
    }

    private fun getHeaderRecyclable(parent: RecyclerView, recyclables: List<Recyclable>): Recyclable? {
        val topChild: View = parent.getChildAt(0) ?: return null

        val topChildAdapterPosition: Int = parent.getChildAdapterPosition(topChild)
        if (topChildAdapterPosition == RecyclerView.NO_POSITION) return null

        val headerAdapterPosition: Int = headerPositionProvider.getHeaderPositionForItem(topChildAdapterPosition, recyclables)
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

    private fun getOrCreateHeaderViewHolder(parent: RecyclerView, dabirva: Dabirva, headerViewType: Int): DataBindingViewHolder {
        val cachedHeaderViewHolder: RecyclerView.ViewHolder? = parent.recycledViewPool.getRecycledView(headerViewType)
        return if (cachedHeaderViewHolder != null) {
            cachedHeaderViewHolder as DataBindingViewHolder
        } else {
            dabirva.createViewHolder(parent, headerViewType)
        }
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View) {
        canvas.save()
        canvas.translate(0F, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(canvas)
        canvas.restore()
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        canvas.save()
        canvas.translate(0F, 0F)
        header.draw(canvas)
        canvas.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (position in 0 until parent.childCount) {
            val child: View = parent.getChildAt(position)
            if (child.bottom > contactPoint && child.top <= contactPoint) {
                return child
            }
        }
        return null
    }

    /**
     * Properly measures and layouts the top sticky header.
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun makeViewSticky(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)

        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun isItemSticky(parent: RecyclerView, recyclables: List<Recyclable>, itemView: View): Boolean {
        val adapterPosition: Int = parent.getChildAdapterPosition(itemView)
        return headerPositionProvider.getHeaderPositionForItem(adapterPosition, recyclables) == adapterPosition
    }

}
