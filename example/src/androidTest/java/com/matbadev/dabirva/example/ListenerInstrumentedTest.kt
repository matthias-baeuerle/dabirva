package com.matbadev.dabirva.example

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.ui.ClickableNoteViewModel
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.useActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class ListenerInstrumentedTest {

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<TestActivity> = activityScenarioRule()

    private val scenario: ActivityScenario<TestActivity>
        get() = activityScenarioRule.scenario

    private lateinit var viewModel: TestActivityViewModel

    @Before
    fun prepare() {
        dataBindingIdlingResourceRule.setScenario(scenario)
        viewModel = scenario.useActivity { it.viewModel }
    }

    @Test
    fun basic_shortClick() {
        var listenerCalled = false
        val itemViewModel = ClickableNoteViewModel(1, "A")
        itemViewModel.onItemClick = {
            listenerCalled = true
        }
        viewModel.dabirvaData.value = DabirvaData(
            items = listOf(itemViewModel),
        )

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
        viewModel.dabirvaData.value = DabirvaData(
            items = listOf(itemViewModel),
        )

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

        viewModel.dabirvaData.value = DabirvaData(
            items = listOf(itemViewModel),
        )

        loopMainThreadUntilIdle()

        itemViewModel.onItemClick = {
            secondListenerCalled = true
        }

        onView(atViewPosition(R.id.recycler_view, 0)).perform(click())

        assert(!firstListenerCalled)
        assert(secondListenerCalled)
    }

}
