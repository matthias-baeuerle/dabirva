package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel

data class HeaderRowViewModel(
    val id: Long,
    val text: String,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_header_row

    override fun entityEquals(other: Any?): Boolean {
        return other is HeaderRowViewModel && id == other.id
    }

}
