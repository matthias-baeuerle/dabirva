package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel
import com.matbadev.dabirva.example.data.NotePriority

data class HeaderRowViewModel(
    val priority: NotePriority,
    val text: String,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_header_row

    override fun entityEquals(other: Any?): Boolean {
        return other is HeaderRowViewModel && priority == other.priority
    }

}
