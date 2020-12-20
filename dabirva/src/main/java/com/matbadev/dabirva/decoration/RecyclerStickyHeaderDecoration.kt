package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.Recyclable

open class RecyclerStickyHeaderDecoration(
    private val delegate: StickyHeaderDelegate,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderViewHolder: DataBindingViewHolder? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter as Dabirva

        val headerRecyclable: Recyclable? = getHeaderRecyclable(parent, adapter)
        var headerViewHolder: DataBindingViewHolder? = currentHeaderViewHolder

        if (headerRecyclable != null) { // Header should be shown
            if (headerViewHolder != null) {
                updateHeaderViewHolder(headerViewHolder, headerRecyclable)
            } else {
                headerViewHolder = getOrCreateHeaderViewHolder(parent, adapter, headerRecyclable.layoutId)
                makeViewSticky(parent, headerViewHolder.itemView)
                currentHeaderViewHolder = headerViewHolder
            }

            val headerView: View = headerViewHolder.itemView

            val contactPoint: Int = headerView.bottom
            val childInContact: View? = getChildInContact(parent, contactPoint)

            if (childInContact != null && isSticky(parent, childInContact)) {
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

    private fun getHeaderRecyclable(parent: RecyclerView, adapter: Dabirva): Recyclable? {
        val topChild: View = parent.getChildAt(0) ?: return null

        val topChildAdapterPosition: Int = parent.getChildAdapterPosition(topChild)
        if (topChildAdapterPosition == RecyclerView.NO_POSITION) return null

        val headerAdapterPosition: Int = delegate.getHeaderPositionForItem(topChildAdapterPosition)
        if (headerAdapterPosition == RecyclerView.NO_POSITION) return null

        return adapter.recyclerData.recyclables[headerAdapterPosition]
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

    private fun getOrCreateHeaderViewHolder(parent: RecyclerView, adapter: Dabirva, headerViewType: Int): DataBindingViewHolder {
        val cachedHeaderViewHolder: RecyclerView.ViewHolder? = parent.recycledViewPool.getRecycledView(headerViewType)
        return if (cachedHeaderViewHolder != null) {
            cachedHeaderViewHolder as DataBindingViewHolder
        } else {
            adapter.createViewHolder(parent, headerViewType)
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
        /*
        var childInContact: View? = null
        for (position in 0 until parent.childCount) {
            var heightTolerance = 0
            val child: View = parent.getChildAt(position)

            // Measure height tolerance with child if child is another header
            if (currentHeaderPos != position) {
                if (isSticky(parent, child)) {
                    heightTolerance = stickyHeaderHeight - child.height
                }
            }

            // Add heightTolerance if child top be in display area
            val childBottomPosition: Int = if (child.top > 0) {
                child.bottom + heightTolerance
            } else {
                child.bottom
            }

            if (childBottomPosition > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
        */

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
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)

        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun isSticky(parent: RecyclerView, child: View): Boolean {
        val adapterPosition: Int = parent.getChildAdapterPosition(child)
        return delegate.getHeaderPositionForItem(adapterPosition) == adapterPosition
    }

}
