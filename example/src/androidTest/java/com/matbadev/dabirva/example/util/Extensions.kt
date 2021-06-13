package com.matbadev.dabirva.example.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario

val Activity.contentView: View
    get() = findViewById(android.R.id.content)

val Activity.rootView: View
    get() = contentView.rootView

fun View.getHierarchyDepthFirst(): Sequence<View> {
    return if (this is ViewGroup) {
        sequenceOf(this) + this.children.flatMap { it.getHierarchyDepthFirst() }
    } else {
        sequenceOf(this)
    }
}

fun <A : Activity, R : Any> ActivityScenario<A>.useActivity(block: (A) -> R): R {
    var result: R? = null
    this.onActivity { activity: A ->
        result = block(activity)
    }
    return result
        ?: throw AssertionError()
}
