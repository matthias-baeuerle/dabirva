package com.matbadev.dabirva.example

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.item.ItemActivity
import com.matbadev.dabirva.example.ui.item.ItemActivityArguments
import com.matbadev.dabirva.example.ui.item.ItemActivityEvent
import com.matbadev.dabirva.example.ui.item.ItemActivityViewModel
import com.matbadev.dabirva.example.util.atAdapterPosition
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.scrollToWithOffset
import org.junit.Test

class ItemInstrumentedTest : BaseInstrumentedTest<ItemActivityArguments, ItemActivityEvent, ItemActivityViewModel, ItemActivity>(
    activityClass = ItemActivity::class,
) {

    override fun provideArguments(): ItemActivityArguments {
        return ItemActivityArguments(
            items = NoteRepository().getNotes(),
        )
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
