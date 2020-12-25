package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListUpdateCallback

internal class ConfigAsyncListDiffer<T>(
    val listUpdateCallback: ListUpdateCallback,
    val config: AsyncDifferConfig<T>,
) : AsyncListDiffer<T>(listUpdateCallback, config)
