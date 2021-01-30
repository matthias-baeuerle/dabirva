package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import java.util.Objects
import java.util.concurrent.Executor

/**
 * Extends [AsyncListDiffer] to make the properties used for configuring available for later access
 * and also provides a proper [equals] and [hashCode] implementation.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class ConfigAsyncListDiffer<T>(
    val listUpdateCallback: ListUpdateCallback,
    val diffCallback: DiffUtil.ItemCallback<T>,
    val backgroundThreadExecutor: Executor,
) : AsyncListDiffer<T>(
    listUpdateCallback,
    AsyncDifferConfig.Builder(diffCallback).setBackgroundThreadExecutor(backgroundThreadExecutor).build(),
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherDiffer = other as? ConfigAsyncListDiffer<*> ?: return false
        return listUpdateCallback == otherDiffer.listUpdateCallback && diffCallback == otherDiffer.diffCallback && backgroundThreadExecutor == otherDiffer.backgroundThreadExecutor
    }

    override fun hashCode(): Int {
        return Objects.hash(listUpdateCallback, diffCallback, backgroundThreadExecutor)
    }

}
