package com.matbadev.dabirva.example.util

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewMatchers {

    fun withLinearLayoutManager(): Matcher<View> {
        return WithLinearLayoutManager()
    }

    fun atAdapterPosition(@IdRes recyclerViewId: Int, adapterPosition: Int): Matcher<View> {
        return AdapterPositionChildMatcher(recyclerViewId, adapterPosition)
    }

    fun atViewPosition(@IdRes recyclerViewId: Int, viewPosition: Int): Matcher<View> {
        return ViewPositionChildMatcher(recyclerViewId, viewPosition)
    }

}

private class WithLinearLayoutManager : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("has a LinearLayoutManager attached")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return item.layoutManager is LinearLayoutManager
    }

}

private abstract class RecyclerViewChildMatcher(
    @IdRes private val recyclerViewId: Int,
) : TypeSafeMatcher<View>() {

    private var currentResources: Resources? = null

    override fun describeTo(description: Description) {
        var idDescription: String = recyclerViewId.toString()
        currentResources?.let { resources ->
            idDescription = try {
                resources.getResourceName(recyclerViewId)
            } catch (ex: Resources.NotFoundException) {
                "$recyclerViewId (resource name not found)"
            }
        }
        description.appendText("with id $idDescription")
    }

    final override fun matchesSafely(item: View): Boolean {
        currentResources = item.resources
        val recyclerView: RecyclerView = item.rootView.findViewById(recyclerViewId)
            ?: return false
        return matchesChild(item, recyclerView)
    }

    abstract fun matchesChild(item: View, recyclerView: RecyclerView): Boolean

}

private class AdapterPositionChildMatcher(
    @IdRes recyclerViewId: Int,
    private val adapterPosition: Int,
) : RecyclerViewChildMatcher(recyclerViewId) {

    override fun describeTo(description: Description) {
        super.describeTo(description)
        description.appendText(" and adapter position $adapterPosition")
    }

    override fun matchesChild(item: View, recyclerView: RecyclerView): Boolean {
        val childViewHolder: ViewHolder = recyclerView.findViewHolderForAdapterPosition(adapterPosition)
            ?: return false
        return childViewHolder.itemView === item
    }

}

private class ViewPositionChildMatcher(
    @IdRes recyclerViewId: Int,
    private val viewPosition: Int,
) : RecyclerViewChildMatcher(recyclerViewId) {

    override fun describeTo(description: Description) {
        super.describeTo(description)
        description.appendText(" and view position $viewPosition")
    }

    override fun matchesChild(item: View, recyclerView: RecyclerView): Boolean {
        val childView: View = recyclerView.getChildAt(viewPosition)
            ?: return false
        return childView === item
    }

}
