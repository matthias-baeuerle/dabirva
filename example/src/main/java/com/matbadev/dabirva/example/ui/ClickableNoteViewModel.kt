package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel

data class ClickableNoteViewModel(
    val id: Long,
    val text: String,
    val onItemClick: (item: ClickableNoteViewModel) -> Unit,
    val onItemLongClick: (item: ClickableNoteViewModel) -> Boolean,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_note_clickable

    override fun entityEquals(other: Any?): Boolean {
        return other is ClickableNoteViewModel && id == other.id
    }

    fun onClick() {
        onItemClick(this)
    }

    fun onLongClick(): Boolean {
        return onItemLongClick(this)
    }

}
