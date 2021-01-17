package com.matbadev.dabirva.example.ui

import android.view.View
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class ClickableNoteViewModel(
    override val id: Long,
    val text: String,
    val onItemClick: (item: ClickableNoteViewModel) -> Unit,
    val onItemLongClick: (item: ClickableNoteViewModel) -> Boolean,
) : ItemViewModel, View.OnClickListener, View.OnLongClickListener {

    override val layoutId: Int = R.layout.item_note_clickable

    override val bindingId: Int = BR.viewModel

    override fun onClick(view: View) {
        onItemClick(this)
    }

    override fun onLongClick(view: View): Boolean {
        return onItemLongClick(this)
    }

}
