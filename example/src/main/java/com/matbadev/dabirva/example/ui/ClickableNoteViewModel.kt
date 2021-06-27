package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel
import com.matbadev.dabirva.example.util.ItemClickListener

data class ClickableNoteViewModel(
    val id: Long,
    val text: String,
) : BaseItemViewModel() {

    override val layoutId: Int = R.layout.item_note_clickable

    lateinit var onItemClick: ItemClickListener<ClickableNoteViewModel>

    lateinit var onItemLongClick: ItemClickListener<ClickableNoteViewModel>

    override fun entityEquals(other: Any?): Boolean {
        return other is ClickableNoteViewModel && id == other.id
    }

    fun onClick() {
        onItemClick(this)
    }

    fun onLongClick(): Boolean {
        onItemLongClick(this)
        return true
    }

}
