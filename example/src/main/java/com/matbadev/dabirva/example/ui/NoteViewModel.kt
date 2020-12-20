package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

class NoteViewModel(
    override val id: Long,
    val text: String,
) : Recyclable {

    override val layoutId: Int = R.layout.item_note

    override val bindingId: Int = BR.viewModel

}
