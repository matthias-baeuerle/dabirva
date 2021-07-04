package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.ItemViewModel

class VerticalStickyHeaderDecoration(
    headerPositionProvider: HeaderPositionProvider,
) : StickyHeaderDecoration(headerPositionProvider) {

    override fun onBoundHeaderViewHolder(
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
    ) {
        super.onBoundHeaderViewHolder(parent, state, dabirva, headerViewHolder)
        layoutHeaderView(parent, headerViewHolder.itemView)
    }

    private fun layoutHeaderView(parent: RecyclerView, headerView: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            headerView.layoutParams.width,
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            headerView.layoutParams.height,
        )

        headerView.measure(childWidthSpec, childHeightSpec)

        headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
    }

    override fun onDrawHeaderOverItems(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
    ) {
        super.onDrawHeaderOverItems(canvas, parent, state, dabirva, headerViewHolder)
        val headerView: View = headerViewHolder.itemView
        val childInContact: View? = getChildInContact(parent, headerView.bottom)
        canvas.save()
        if (childInContact != null && isItemSticky(parent, dabirva.items, childInContact)) {
            canvas.translate(0F, (childInContact.top - headerView.height).toFloat())
        } else {
            canvas.translate(0F, 0F)
        }
        headerView.draw(canvas)
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

    private fun isItemSticky(parent: RecyclerView, items: List<ItemViewModel>, itemView: View): Boolean {
        val adapterPosition: Int = parent.getChildAdapterPosition(itemView)
        return headerPositionProvider.getHeaderPositionForItem(adapterPosition, items) == adapterPosition
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VerticalStickyHeaderDecoration) return false
        return headerPositionProvider == other.headerPositionProvider
    }

    override fun hashCode(): Int {
        return headerPositionProvider.hashCode()
    }

    override fun toString(): String {
        return "VerticalStickyHeaderDecoration(headerPositionProvider=$headerPositionProvider)"
    }

}
