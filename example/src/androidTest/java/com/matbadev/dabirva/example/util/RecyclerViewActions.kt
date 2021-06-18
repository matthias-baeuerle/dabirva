package com.matbadev.dabirva.example.util

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

fun scrollToWithOffset(position: Int, offset: Int = 0): ViewAction {
    return OffsetScrollRecyclerViewAction(position, offset)
}

fun registerAdapterDataObserver(observer: AdapterDataObserver): ViewAction {
    return RegisterAdapterDataObserver(observer)
}

private class OffsetScrollRecyclerViewAction(
    private val position: Int,
    private val offset: Int,
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return allOf(isDisplayed(), withLinearLayoutManager())
    }

    override fun getDescription(): String {
        return "scroll RecyclerView to position $position with offset $offset"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(position, offset)
        uiController.loopMainThreadUntilIdle()
    }

}

private class RegisterAdapterDataObserver(
    private val observer: AdapterDataObserver,
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return allOf(isDisplayed(), isAssignableFrom(RecyclerView::class.java))
    }

    override fun getDescription(): String {
        return "register an AdapterDataObserver"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        val adapter: RecyclerView.Adapter<*> = recyclerView.adapter!!
        adapter.registerAdapterDataObserver(observer)
    }

}
