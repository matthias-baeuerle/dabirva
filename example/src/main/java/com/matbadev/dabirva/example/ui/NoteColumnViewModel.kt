package com.matbadev.dabirva.example.ui

import androidx.annotation.ColorInt
import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteColumnViewModel(
    override val id: Long,
    val text: String,
    @ColorInt val color: Int,
) : Recyclable {

    override val layoutId: Int = R.layout.item_note_column

    override val bindingId: Int = BR.viewModel

}
