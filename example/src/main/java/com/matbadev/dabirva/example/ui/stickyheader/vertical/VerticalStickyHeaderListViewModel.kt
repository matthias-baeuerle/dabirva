package com.matbadev.dabirva.example.ui.stickyheader.vertical

import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.decoration.ItemHeaderProvider
import com.matbadev.dabirva.decoration.VerticalStickyHeaderDecoration
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NotePriority
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.HeaderRowViewModel
import com.matbadev.dabirva.example.ui.NoteRowViewModel

class VerticalStickyHeaderListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<VerticalStickyHeaderListEvent, VerticalStickyHeaderListArguments>() {

    lateinit var dabirvaData: DabirvaData

    override fun initWithArguments(arguments: VerticalStickyHeaderListArguments?) {
        super.initWithArguments(arguments)
        dabirvaData = DabirvaData(
            recyclables = noteRepository.getNotes()
                .groupBy { note -> note.priority }
                .flatMap { noteEntry -> buildVerticalGroupViewModels(noteEntry) },
            decorations = listOf(
                VerticalStickyHeaderDecoration(
                    headerPositionProvider = ItemHeaderProvider { it is HeaderRowViewModel },
                ),
            ),
        )
    }

    private fun buildVerticalGroupViewModels(noteEntry: Map.Entry<NotePriority, List<Note>>): Sequence<Recyclable> {
        return sequence {
            yield(HeaderRowViewModel(
                id = noteEntry.key.ordinal.toLong(),
                text = noteEntry.key.name,
            ))
            noteEntry.value.forEach { note: Note ->
                yield(NoteRowViewModel(
                    id = note.id,
                    text = note.text,
                    color = note.color,
                ))
            }
        }
    }

}
