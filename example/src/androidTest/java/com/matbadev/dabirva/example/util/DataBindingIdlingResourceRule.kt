package com.matbadev.dabirva.example.util

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DataBindingIdlingResourceRule : TestWatcher() {

    private val idlingResource = DataBindingIdlingResource()

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    fun monitorActivity(scenario: ActivityScenario<out Activity>) {
        idlingResource.monitorActivity(scenario)
    }

}
