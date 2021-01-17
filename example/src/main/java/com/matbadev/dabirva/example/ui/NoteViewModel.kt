package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteViewModel(
    override val id: Long,
    val text: String,
) : ItemViewModel {

    override val layoutId: Int = R.layout.item_note

    override val bindingId: Int = BR.viewModel

}
