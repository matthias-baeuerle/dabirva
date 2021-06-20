package com.matbadev.dabirva.util

import androidx.databinding.ObservableField

class NonNullObservableField<T : Any>(
    initialValue: T,
) : ObservableField<T>(initialValue) {

    var value: T
        get() = this.get()
        set(value) = this.set(value)

    override fun get(): T {
        return super.get() ?: throw AssertionError("ObservableField.get() returned null although initial value was set")
    }

    override fun set(value: T) {
        super.set(value)
    }

}
