package com.matbadev.dabirva.example.util

import androidx.databinding.ObservableField

var <T> ObservableField<T>.value
    get() = this.get()
    set(value) = this.set(value)
