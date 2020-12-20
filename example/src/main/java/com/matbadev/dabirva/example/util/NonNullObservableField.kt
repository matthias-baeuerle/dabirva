package com.matbadev.dabirva.example.util

import androidx.databinding.ObservableField

class NonNullObservableField<T : Any>(
    initialValue: T,
) : ObservableField<T>(initialValue) {

    var value: T
        get() = this.get()
        set(value) = this.set(value)


    override fun get(): T {
        return super.get() ?: throw AssertionError()
    }

    // Overwrite required to mark value as non-null
    @Suppress("RedundantOverride")
    override fun set(value: T) {
        super.set(value)
    }

}
