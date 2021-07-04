package com.matbadev.dabirva

import androidx.annotation.VisibleForTesting

object DabirvaConfig {

    @JvmStatic
    @Volatile
    var locked: Boolean = false
        private set

    @JvmStatic
    var factory: DabirvaFactory = DabirvaFactory { Dabirva() }
        set(newFactory) {
            check(!locked)
            field = newFactory
        }

    @JvmStatic
    fun lock() {
        locked = true
    }

    @VisibleForTesting
    internal fun reset() {
        locked = false
    }

}
