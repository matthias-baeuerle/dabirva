package com.matbadev.dabirva.example

import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
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
import com.matbadev.dabirva.example.NoteViewModels.D
import com.matbadev.dabirva.example.NoteViewModels.E
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivity
import com.matbadev.dabirva.example.ui.diffing.ItemDiffingActivityViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.registerAdapterDataObserver
import com.matbadev.dabirva.example.util.useActivity
import com.matbadev.dabirva.example.util.withChildCount
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class ItemDiffingInstrumentedTest {

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Mock
    private lateinit var adapterDataObserver: AdapterDataObserver

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
            initialItems = listOf(A, B, C),
            updatedItems = listOf(A, B, D, C),
        )
        verify(adapterDataObserver).onItemRangeInserted(2, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun insertMultiple() {
        runTest(
            initialItems = listOf(A, B, D),
            updatedItems = listOf(A, B, C, D, E),
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeInserted(3, 1)
            verify(adapterDataObserver).onItemRangeInserted(2, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    private fun runTest(initialItems: List<NoteViewModel>, updatedItems: List<NoteViewModel>) {
        onView(withId(R.id.recycler_view)) //
            .check(matches(withChildCount(0))) //

        viewModel.dabirvaData.value = DabirvaData(
            items = initialItems,
        )

        onView(withId(R.id.recycler_view)) //
            .check(matches(withChildCount(initialItems.size)))
        initialItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(R.id.recycler_view, index)) //
                .check(matches(withText(noteViewModel.text)))
        }
        onView(withId(R.id.recycler_view)) //
            .perform(registerAdapterDataObserver(adapterDataObserver))

        viewModel.dabirvaData.value = DabirvaData(
            items = updatedItems,
        )

        onView(withId(R.id.recycler_view)) //
            .check(matches(withChildCount(updatedItems.size)))
        updatedItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(R.id.recycler_view, index)) //
                .check(matches(withText(noteViewModel.text)))
        }
    }

}
