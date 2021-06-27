package com.matbadev.dabirva.example.util

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.isRoot

fun loopMainThreadUntilIdle() {
    onView(isRoot()).check(EmptyAssertion)
}

private object EmptyAssertion : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
    }
}
