package com.matbadev.dabirva.example.ui.stickyheader

import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.RecyclerData
import com.matbadev.dabirva.decoration.ItemHeaderProvider
import com.matbadev.dabirva.decoration.StickyHeaderDecoration
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NotePriority
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.NoteHeaderViewModel
import com.matbadev.dabirva.example.ui.NoteRowViewModel

class StickyHeaderListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<StickyHeaderListEvent>() {

    val recyclerData = RecyclerData(
        recyclables = noteRepository.getNotes()
            .groupBy { note: Note -> note.priority }
            .flatMap(::buildGroupViewModels),
        decorations = listOf(
            StickyHeaderDecoration(
                headerPositionProvider = ItemHeaderProvider { it is NoteHeaderViewModel },
            ).asRecyclerViewDecoration(),
        ),
    )

    private fun buildGroupViewModels(entry: Map.Entry<NotePriority, List<Note>>): Sequence<Recyclable> = sequence {
        yield(
            NoteHeaderViewModel(
                id = entry.key.ordinal.toLong(),
                text = entry.key.name,
            )
        )
        entry.value.forEach { note: Note ->
            yield(
                NoteRowViewModel(
                    id = note.id,
                    text = note.text,
                )
            )
        }
    }

}
