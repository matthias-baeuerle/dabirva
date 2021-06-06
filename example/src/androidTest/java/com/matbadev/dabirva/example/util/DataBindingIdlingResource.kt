package com.matbadev.dabirva.example.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingResource
import java.util.UUID

/**
 * An espresso idling resource implementation that reports idle status for all data binding
 * layouts. Data Binding uses a mechanism to post messages which Espresso doesn't track yet.
 *
 * Since this application runs UI tests at the fragment layer, this relies on implementations
 * calling [monitorActivity] with a [FragmentScenario], thereby monitoring all bindings in that
 * fragment and any child views.
 */
class DataBindingIdlingResource : IdlingResource {

    // list of registered callbacks
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()

    // give it a unique id to workaround an espresso bug where you cannot register/unregister
    // an idling resource w/ the same name.
    private val id = UUID.randomUUID().toString()

    // holds whether isIdle is called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private var wasNotIdle = false

    private lateinit var scenario: ActivityScenario<out Activity>

    override fun getName() = "DataBinding $id"

    /**
     * Sets the fragment from a [ActivityScenario] to be used from [DataBindingIdlingResource].
     */
    fun monitorActivity(scenario: ActivityScenario<out Activity>) {
        this.scenario = scenario
    }

    override fun isIdleNow(): Boolean {
        val idle = !getBindings().any { it.hasPendingBindings() }
        if (idle) {
            if (wasNotIdle) { // notify observers to avoid espresso race detector
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true // check next frame
            scenario.onActivity { activity ->
                activity.rootView?.postDelayed({
                    if (activity.rootView != null) {
                        isIdleNow
                    }
                }, 16)
            }
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }

    /**
     * Find all binding classes in all currently available fragments.
     */
    private fun getBindings(): List<ViewDataBinding> {
        lateinit var bindings: List<ViewDataBinding>
        scenario.onActivity { activity ->
            bindings = activity.rootView!!.flattenHierarchy().mapNotNull { view ->
                DataBindingUtil.getBinding<ViewDataBinding>(view)
            }
        }
        return bindings
    }

    private fun View.flattenHierarchy(): List<View> = if (this is ViewGroup) {
        listOf(this) + children.map { it.flattenHierarchy() }.flatten()
    } else {
        listOf(this)
    }
}

val Activity.rootView: View?
    get() = findViewById(android.R.id.content)
