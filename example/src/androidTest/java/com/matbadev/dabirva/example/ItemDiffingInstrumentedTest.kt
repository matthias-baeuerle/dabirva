package com.matbadev.dabirva.example

import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.NoteViewModels.B
import com.matbadev.dabirva.example.NoteViewModels.C
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivity
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivityViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.useActivity
import com.matbadev.dabirva.example.util.withChildCount
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDiffingInstrumentedTest {

    companion object {
        @IdRes
        private const val RECYCLER_VIEW_ID: Int = R.id.recycler_view
    }

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private lateinit var viewModel: ItemDiffingActivityViewModel

    @Before
    fun prepare() {
        val scenario = ActivityScenario.launch(ItemDiffingActivity::class.java)
        dataBindingIdlingResourceRule.setScenario(scenario)
        viewModel = scenario.useActivity { it.viewModel }
    }

    @Test
    fun insertSingle() {
        runTest(
            initialItems = listOf(A, C),
            updatedItems = listOf(A, B, C),
        )
    }

    private fun runTest(initialItems: List<NoteViewModel>, updatedItems: List<NoteViewModel>) {
        onView(withId(RECYCLER_VIEW_ID)).check(matches(withChildCount(0)))

        viewModel.dabirvaData.value = DabirvaData(
            items = initialItems,
        )

        onView(withId(RECYCLER_VIEW_ID)).check(matches(withChildCount(initialItems.size)))
        initialItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(RECYCLER_VIEW_ID, index)).check(matches(withText(noteViewModel.text)))
        }

        viewModel.dabirvaData.value = DabirvaData(
            items = updatedItems,
        )

        onView(withId(RECYCLER_VIEW_ID)).check(matches(withChildCount(updatedItems.size)))
        updatedItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(RECYCLER_VIEW_ID, index)).check(matches(withText(noteViewModel.text)))
        }
    }

}
