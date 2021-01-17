package com.matbadev.dabirva.example.ui.click

import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.ShowToastEvent
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.ClickableNoteViewModel

class ClickListActivityViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<ClickListActivityEvent, ClickListActivityArguments>() {

    lateinit var dabirvaData: DabirvaData

    override fun initWithArguments(arguments: ClickListActivityArguments?) {
        super.initWithArguments(arguments)
        dabirvaData = DabirvaData(
            items = noteRepository.getNotes().map { note: Note ->
                ClickableNoteViewModel(
                    id = note.id,
                    text = note.text,
                    onItemClick = ::onItemClick,
                    onItemLongClick = ::onItemLongClick,
                )
            },
        )
    }

    private fun onItemClick(item: ClickableNoteViewModel) {
        queueCommonUiEvent(ShowToastEvent(
            messageProvider = { "Clicked: ${item.text}" },
        ))
    }

    private fun onItemLongClick(item: ClickableNoteViewModel): Boolean {
        queueCommonUiEvent(ShowToastEvent(
            messageProvider = { "Long clicked: ${item.text}" },
        ))
        return true
    }

}
