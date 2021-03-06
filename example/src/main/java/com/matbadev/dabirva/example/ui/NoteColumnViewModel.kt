package com.matbadev.dabirva.example.ui

import androidx.annotation.ColorInt
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel

data class NoteColumnViewModel(
    val id: Long,
    val text: String,
    @ColorInt val color: Int,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_note_column

    override fun entityEquals(other: Any?): Boolean {
        return other is NoteColumnViewModel && id == other.id
    }

}
