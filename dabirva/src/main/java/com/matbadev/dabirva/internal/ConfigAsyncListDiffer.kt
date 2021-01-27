package com.matbadev.dabirva.internal

import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import java.lang.reflect.Field
import java.util.Objects
import java.util.concurrent.Executor

internal class ConfigAsyncListDiffer<T>(
    val listUpdateCallback: ListUpdateCallback,
    val config: AsyncDifferConfig<T>,
) : AsyncListDiffer<T>(listUpdateCallback, config) {

    private val mainThreadExecutorField: Field by lazy {
        val field = AsyncListDiffer::class.java.getDeclaredField("mMainThreadExecutor")
        field.isAccessible = true
        field
    }

    @VisibleForTesting
    var mainThreadExecutor: Executor
        get() = mainThreadExecutorField.get(this) as Executor
        set(value) = mainThreadExecutorField.set(this, value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherDiffer = other as? ConfigAsyncListDiffer<*> ?: return false
        return listUpdateCallback == otherDiffer.listUpdateCallback && config == otherDiffer.config
    }

    override fun hashCode(): Int {
        return Objects.hash(listUpdateCallback, config)
    }

}
