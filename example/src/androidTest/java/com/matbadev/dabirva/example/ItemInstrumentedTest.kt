package com.matbadev.dabirva.example

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.NoteGenerator
import com.matbadev.dabirva.example.ui.item.ItemActivity
import com.matbadev.dabirva.example.ui.item.ItemActivityArguments
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.atAdapterPosition
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.scrollToWithOffset
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemInstrumentedTest {

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<ItemActivity> = activityScenarioRule(
        intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, ItemActivity::class.java).apply {
            val arguments = ItemActivityArguments(
                items = NoteGenerator.generateNotes(),
            )
            putExtra(BaseScreenViewModel.SCREEN_ARGUMENTS_KEY, arguments)
        },
    )

    private val scenario: ActivityScenario<ItemActivity>
        get() = activityScenarioRule.scenario

    @Before
    fun prepare() {
        dataBindingIdlingResourceRule.setScenario(scenario)
    }

    @Test
    fun basic() {
        (0..5).forEach { layoutPosition: Int ->
            onView(atAdapterPosition(R.id.recycler_view, layoutPosition)) //
                .check(matches(withText("Note $layoutPosition")))
        }
    }

    @Test
    fun scrolling() {
        val scrollPosition = 20
        onView(withId(R.id.recycler_view)) //
            .perform(scrollToWithOffset(scrollPosition))
        (0..5).forEach { layoutPosition: Int ->
            onView(atViewPosition(R.id.recycler_view, layoutPosition)) //
                .check(matches(withText("Note ${scrollPosition + layoutPosition}")))
        }
    }

}
