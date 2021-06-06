package com.matbadev.dabirva.example.util

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DataBindingIdlingResourceRule : TestWatcher() {

    private val idlingRegistry = IdlingRegistry.getInstance()

    private val idlingResource = DataBindingIdlingResource()

    override fun starting(description: Description) {
        super.starting(description)
        idlingRegistry.register(idlingResource)
    }

    override fun finished(description: Description) {
        super.finished(description)
        idlingRegistry.unregister(idlingResource)
    }

    fun setScenario(scenario: ActivityScenario<out Activity>) {
        idlingResource.setScenario(scenario)
    }

}
