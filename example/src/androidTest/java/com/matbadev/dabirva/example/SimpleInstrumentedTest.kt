package com.matbadev.dabirva.example

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matbadev.dabirva.example.ui.simple.SimpleListActivity
import com.matbadev.dabirva.example.util.atAdapterPosition
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.scrollToWithOffset
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleInstrumentedTest {

    @get:Rule
    var activityRule = activityScenarioRule<SimpleListActivity>()

    @Test
    fun basic() {
        (0..5).forEach { layoutPosition: Int ->
            onView(atAdapterPosition(R.id.simple_list_recycler, layoutPosition)) //
                .check(matches(withText("Note $layoutPosition")))
        }
    }

    @Test
    fun scrolling() {
        val scrollPosition = 20
        onView(withId(R.id.simple_list_recycler)) //
            .perform(scrollToWithOffset(scrollPosition))
        (0..5).forEach { layoutPosition: Int ->
            onView(atViewPosition(R.id.simple_list_recycler, layoutPosition)) //
                .check(matches(withText("Note ${scrollPosition + layoutPosition}")))
        }
    }

}
