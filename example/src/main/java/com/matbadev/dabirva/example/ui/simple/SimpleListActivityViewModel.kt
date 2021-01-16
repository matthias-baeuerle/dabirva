package com.matbadev.dabirva.example.ui.simple

import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.NoteViewModel

class SimpleListActivityViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<SimpleListActivityEvent, SimpleListActivityArguments>() {

    lateinit var dabirvaData: DabirvaData

    override fun initWithArguments(arguments: SimpleListActivityArguments?) {
        DabirvaData(
            recyclables = noteRepository.getNotes().map { note: Note ->
                NoteViewModel(
                    id = note.id,
                    text = note.text,
                    color = note.color,
                )
            },
        )
    }

}
