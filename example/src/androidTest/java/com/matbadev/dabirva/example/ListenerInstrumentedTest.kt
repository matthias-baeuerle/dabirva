package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import com.matbadev.dabirva.example.ui.ClickableNoteViewModel
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import org.junit.Test

class ListenerInstrumentedTest : BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
    activityClass = TestActivity::class,
) {

    @Test
    fun basic_shortClick() {
        var listenerCalled = false
        val itemViewModel = ClickableNoteViewModel(1, "A")
        itemViewModel.onItemClick = {
            listenerCalled = true
        }
        viewModel.items.value = listOf(itemViewModel)

        onView(atViewPosition(R.id.recycler_view, 0)).perform(click())

        assert(listenerCalled)
    }

    @Test
    fun basic_longClick() {
        var listenerCalled = false
        val itemViewModel = ClickableNoteViewModel(1, "A")
        itemViewModel.onItemLongClick = {
            listenerCalled = true
        }
        viewModel.items.value = listOf(itemViewModel)

        onView(atViewPosition(R.id.recycler_view, 0)).perform(longClick())

        assert(listenerCalled)
    }

    @Test
    fun changeListenerAfterBindingsExecuted() {
        var firstListenerCalled = false
        var secondListenerCalled = false

        val itemViewModel = ClickableNoteViewModel(1, "A")
        itemViewModel.onItemClick = {
            firstListenerCalled = true
        }

        viewModel.items.value = listOf(itemViewModel)

        loopMainThreadUntilIdle()

        itemViewModel.onItemClick = {
            secondListenerCalled = true
        }

        onView(atViewPosition(R.id.recycler_view, 0)).perform(click())

        assert(!firstListenerCalled)
        assert(secondListenerCalled)
    }

}
