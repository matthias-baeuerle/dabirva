package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class HeaderViewModel(
    override val id: Long,
    val text: String,
) : Recyclable {

    override val layoutId: Int = R.layout.item_header

    override val bindingId: Int = BR.viewModel

}
