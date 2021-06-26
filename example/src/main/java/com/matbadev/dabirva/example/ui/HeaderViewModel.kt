package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel

data class HeaderViewModel(
    val id: Long,
    val text: String,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_header

    override fun entityEquals(other: Any?): Boolean {
        return other is HeaderViewModel && id == other.id
    }

}
