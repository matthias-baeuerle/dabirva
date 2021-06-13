package com.matbadev.dabirva.example.util

import android.app.Activity
import android.view.Choreographer
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingResource
import java.util.UUID

/**
 * An espresso idling resource implementation that reports idle status for all data binding layouts.
 * Data Binding uses a mechanism to post messages which Espresso doesn't track yet.
 *
 * Inspired by:
 * [architecture-components-samples/DataBindingIdlingResource.kt](https://github.com/android/architecture-components-samples/blob/1d7a759f742e8bdaf1eb4531e38ea9270301c577/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/util/DataBindingIdlingResource.kt)
 */
class DataBindingIdlingResource : IdlingResource {

    /**
     * List of registered callbacks.
     */
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()

    /**
     * Unique id to workaround an espresso bug
     * where you cannot register/unregister an idling resource with the same name.
     */
    private val id = UUID.randomUUID()

    /**
     * Holds whether isIdle is called and the result was false.
     * We track this to avoid calling onTransitionToIdle callbacks
     * if Espresso never thought we were idle in the first place.
     */
    private var wasNotIdle = false

    private var scenario: ActivityScenario<out Activity>? = null

    override fun getName(): String {
        return "DataBinding $id"
    }

    override fun isIdleNow(): Boolean {
        val idle: Boolean = findAllViewBindings().none { it.hasPendingBindings() }
        if (idle) {
            if (wasNotIdle) {
                // Notify observers to avoid espresso race detector
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true
            // Check next frame
            Choreographer.getInstance()
                .postFrameCallback { isIdleNow }
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }

    fun setScenario(scenario: ActivityScenario<out Activity>?) {
        this.scenario = scenario
    }

    private fun findAllViewBindings(): Sequence<ViewDataBinding> {
        val currentScenario = checkNotNull(scenario)
        val rootView: View = currentScenario.useActivity { it.rootView }
        return rootView.getHierarchyDepthFirst()
            .mapNotNull { view: View -> DataBindingUtil.getBinding(view) }
    }

}
