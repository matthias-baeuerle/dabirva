package com.matbadev.dabirva.example.ui

import android.view.View
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseItemViewModel

data class ClickableNoteViewModel(
    val id: Long,
    val text: String,
    val onItemClick: (item: ClickableNoteViewModel) -> Unit,
    val onItemLongClick: (item: ClickableNoteViewModel) -> Boolean,
) : BaseItemViewModel(), View.OnClickListener, View.OnLongClickListener {

    override val layoutId: Int = R.layout.item_note_clickable

    override fun entityEquals(other: Any?): Boolean {
        return other is ClickableNoteViewModel && id == other.id
    }

    override fun onClick(view: View) {
        onItemClick(this)
    }

    override fun onLongClick(view: View): Boolean {
        return onItemLongClick(this)
    }

}
