package com.matbadev.dabirva.decoration

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DataBindingViewHolder
import com.matbadev.dabirva.Recyclable

class HorizontalStickyHeaderDecoration(
    headerPositionProvider: HeaderPositionProvider,
) : StickyHeaderDecoration(headerPositionProvider) {

    override fun onBoundHeaderViewHolder(
        parent: RecyclerView,
        state: RecyclerView.State,
        dabirva: Dabirva,
        headerViewHolder: DataBindingViewHolder,
        headerRecyclable: Recyclable,
    ) {
        super.onBoundHeaderViewHolder(parent, state, dabirva, headerViewHolder, headerRecyclable)
        layoutHeaderView(parent, headerViewHolder.itemView)
    }

    private fun layoutHeaderView(parent: RecyclerView, headerView: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.UNSPECIFIED)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)

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
        val headerView: View = headerViewHolder.itemView
        val childInContact: View? = getChildInContact(parent, headerView.right)
        canvas.save()
        if (childInContact != null && isItemSticky(parent, dabirva.recyclerData.recyclables, childInContact)) {
            canvas.translate((childInContact.left - headerView.width).toFloat(), 0F)
        } else {
            canvas.translate(0F, 0F)
        }
        headerView.draw(canvas)
        canvas.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (position in 0 until parent.childCount) {
            val child: View = parent.getChildAt(position)
            if (child.right > contactPoint && child.left <= contactPoint) {
                return child
            }
        }
        return null
    }

    private fun isItemSticky(parent: RecyclerView, recyclables: List<Recyclable>, itemView: View): Boolean {
        val adapterPosition: Int = parent.getChildAdapterPosition(itemView)
        return headerPositionProvider.getHeaderPositionForItem(adapterPosition, recyclables) == adapterPosition
    }

}
