package com.matbadev.dabirva.example

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.matbadev.dabirva.example.base.BaseActivity
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.util.DataBindingIdlingResourceRule
import com.matbadev.dabirva.example.util.useActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentedTest<ARGS : Parcelable, E, VM : BaseScreenViewModel<ARGS, E>, A : BaseActivity<ARGS, E, VM>>(
    activityClass: KClass<A>,
) {

    protected val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<A> = ActivityScenarioRule<A>(
        Intent(targetContext, activityClass.java).apply {
            val arguments: ARGS? = provideArguments()
            putExtra(BaseScreenViewModel.SCREEN_ARGUMENTS_KEY, arguments)
        },
    )

    protected val scenario: ActivityScenario<A>
        get() = activityScenarioRule.scenario

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    protected lateinit var viewModel: VM

    open fun provideArguments(): ARGS? {
        return null
    }

    @Before
    fun prepare() {
        dataBindingIdlingResourceRule.setScenario(scenario)
        viewModel = scenario.useActivity { it.viewModel }
    }

}
