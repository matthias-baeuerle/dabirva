package com.matbadev.dabirva.example.ui.click

import android.os.Parcelable
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.ShowToastEvent
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.ClickableNoteViewModel

class ClickListActivityViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<Parcelable, ClickListActivityEvent>() {

    lateinit var items: List<ItemViewModel>

    override fun initWithArguments(arguments: Parcelable?) {
        super.initWithArguments(arguments)
        items = noteRepository.getNotes().map { note: Note ->
            ClickableNoteViewModel(
                id = note.id,
                text = note.text,
            ).apply {
                onItemClick = ::onItemClick
                onItemLongClick = ::onItemLongClick
            }
        }
    }

    private fun onItemClick(item: ClickableNoteViewModel) {
        queueCommonUiEvent(ShowToastEvent(
            messageProvider = { "Clicked: ${item.text}" },
        ))
    }

    private fun onItemLongClick(item: ClickableNoteViewModel) {
        queueCommonUiEvent(ShowToastEvent(
            messageProvider = { "Long clicked: ${item.text}" },
        ))
    }

}
