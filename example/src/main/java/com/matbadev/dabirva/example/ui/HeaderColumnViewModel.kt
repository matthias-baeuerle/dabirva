package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class HeaderColumnViewModel(
    override val id: Long,
    val text: String,
) : ItemViewModel {

    override val layoutId: Int = R.layout.item_header_column

    override val bindingId: Int = BR.viewModel

}
