package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteRowViewModel(
    override val id: Long,
    val text: String,
) : Recyclable {

    override val layoutId: Int = R.layout.item_note_row

    override val bindingId: Int = BR.viewModel

}