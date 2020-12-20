package com.matbadev.dabirva.example.ui.simple

import com.matbadev.dabirva.RecyclerData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.SynchronousNoteRepository
import com.matbadev.dabirva.example.ui.NoteViewModel

@Suppress("CanBeParameter")
class SimpleListViewModel(
    private val synchronousNoteRepository: SynchronousNoteRepository,
) : BaseScreenViewModel<SimpleListEvent>() {

    val recyclerData = RecyclerData(
        recyclables = synchronousNoteRepository.getNotes().map { note: Note ->
            NoteViewModel(
                id = note.id,
                text = note.text,
            )
        }
    )

}
