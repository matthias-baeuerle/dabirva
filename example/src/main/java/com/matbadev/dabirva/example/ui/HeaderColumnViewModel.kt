package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel
import com.matbadev.dabirva.example.data.NotePriority

data class HeaderColumnViewModel(
    val priority: NotePriority,
    val text: String,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_header_column

    override fun entityEquals(other: Any?): Boolean {
        return other is HeaderColumnViewModel && priority == other.priority
    }

}
