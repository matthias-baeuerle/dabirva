package com.matbadev.dabirva.example.ui.simple

import com.matbadev.dabirva.RecyclerData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.NoteViewModel

class SimpleListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<SimpleListEvent>() {

    val recyclerData = RecyclerData(
        recyclables = noteRepository.getNotes().map { note: Note ->
            NoteViewModel(
                id = note.id,
                text = note.text,
            )
        },
    )

}
