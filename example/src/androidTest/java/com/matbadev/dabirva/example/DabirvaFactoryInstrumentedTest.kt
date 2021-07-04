package com.matbadev.dabirva.example

import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.ui.item.ItemActivity
import com.matbadev.dabirva.example.ui.item.ItemActivityArguments
import com.matbadev.dabirva.example.ui.item.ItemActivityEvent
import com.matbadev.dabirva.example.ui.item.ItemActivityViewModel
import com.matbadev.dabirva.example.util.TrampolineExecutor
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class DabirvaFactoryInstrumentedTest : BaseInstrumentedTest<ItemActivityArguments, ItemActivityEvent, ItemActivityViewModel, ItemActivity>(
    activityClass = ItemActivity::class,
) {

    @After
    fun reset() {
        DabirvaConfig.factory = DabirvaFactory { Dabirva() }
    }

    @Test
    fun overwriteDefaultDiffExecutor() {
        val executor = TrampolineExecutor()
        DabirvaConfig.factory = DabirvaFactory {
            Dabirva().apply {
                diffExecutor = executor
            }
        }
        scenario.recreate()

        viewModel.items.value = listOf(A)
        loopMainThreadUntilIdle()

        assertEquals(1, executor.executedCommandsCount)
    }

}
