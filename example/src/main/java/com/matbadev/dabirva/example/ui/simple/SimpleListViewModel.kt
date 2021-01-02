package com.matbadev.dabirva.example.ui.simple

import com.matbadev.dabirva.RecyclerData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.NoteRowViewModel
import com.matbadev.dabirva.util.NonNullObservableField

class SimpleListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<SimpleListEvent>() {

    val recyclerData = NonNullObservableField(
        RecyclerData(
            recyclables = noteRepository.getNotes().map { note: Note ->
                NoteRowViewModel(
                    id = note.id,
                    text = note.text,
                )
            },
        )
    )

}
