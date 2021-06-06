package com.matbadev.dabirva.example

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivity
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivityViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.atViewPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDiffingInstrumentedTest {

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    lateinit var viewModel: ItemDiffingActivityViewModel

    @Before
    fun prepare() {
        val scenario = ActivityScenario.launch(ItemDiffingActivity::class.java)
        dataBindingIdlingResourceRule.setScenario(scenario)
        scenario.onActivity { activity: ItemDiffingActivity ->
            viewModel = activity.viewModel
        }
    }

    @Test
    fun insertSingle() {
        viewModel.dabirvaData.value = DabirvaData(
            items = listOf(
                NoteViewModel(0, "A"),
                NoteViewModel(2, "C"),
            ),
        )

        onView(atViewPosition(R.id.recycler_view, 0)) //
            .check(matches(withText("A")))
        onView(atViewPosition(R.id.recycler_view, 1)) //
            .check(matches(withText("C")))

        viewModel.dabirvaData.value = DabirvaData(
            items = listOf(
                NoteViewModel(0, "A"),
                NoteViewModel(1, "B"),
                NoteViewModel(2, "C"),
            ),
        )

        onView(atViewPosition(R.id.recycler_view, 0)) //
            .check(matches(withText("A")))
        onView(atViewPosition(R.id.recycler_view, 1)) //
            .check(matches(withText("B")))
        onView(atViewPosition(R.id.recycler_view, 2)) //
            .check(matches(withText("C")))
    }

}
