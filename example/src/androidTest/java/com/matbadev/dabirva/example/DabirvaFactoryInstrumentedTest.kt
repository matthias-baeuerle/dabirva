package com.matbadev.dabirva.example

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.ui.item.ItemActivity
import com.matbadev.dabirva.example.ui.item.ItemActivityViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.TrampolineExecutor
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.useActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DabirvaFactoryInstrumentedTest {

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<ItemActivity> = activityScenarioRule()

    private val scenario: ActivityScenario<ItemActivity>
        get() = activityScenarioRule.scenario

    private lateinit var viewModel: ItemActivityViewModel

    @Before
    fun prepare() {
        dataBindingIdlingResourceRule.setScenario(scenario)
        viewModel = scenario.useActivity { it.viewModel }
    }

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
