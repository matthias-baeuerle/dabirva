package com.matbadev.dabirva.example.ui.stickyheader

import com.matbadev.dabirva.LinearOrientation
import com.matbadev.dabirva.Recyclable
import com.matbadev.dabirva.RecyclerData
import com.matbadev.dabirva.decoration.HorizontalStickyHeaderDecoration
import com.matbadev.dabirva.decoration.ItemHeaderProvider
import com.matbadev.dabirva.decoration.StickyHeaderDecoration
import com.matbadev.dabirva.decoration.VerticalStickyHeaderDecoration
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NotePriority
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.HeaderColumnViewModel
import com.matbadev.dabirva.example.ui.HeaderRowViewModel
import com.matbadev.dabirva.example.ui.NoteColumnViewModel
import com.matbadev.dabirva.example.ui.NoteRowViewModel

class StickyHeaderListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<StickyHeaderListEvent, StickyHeaderListArguments>() {

    lateinit var recyclerData: RecyclerData

    lateinit var recyclerOrientation: LinearOrientation

    override fun initWithArguments(arguments: StickyHeaderListArguments?) {
        super.initWithArguments(arguments)
        recyclerOrientation = arguments!!.listOrientation
        val stickyHeaderDecoration: StickyHeaderDecoration = when (recyclerOrientation) {
            LinearOrientation.HORIZONTAL -> HorizontalStickyHeaderDecoration(
                headerPositionProvider = ItemHeaderProvider { it is HeaderColumnViewModel }
            )
            LinearOrientation.VERTICAL -> VerticalStickyHeaderDecoration(
                headerPositionProvider = ItemHeaderProvider { it is HeaderRowViewModel }
            )
        }
        recyclerData = RecyclerData(
            recyclables = noteRepository.getNotes()
                .groupBy { note -> note.priority }
                .flatMap { noteEntry -> buildGroupViewModels(noteEntry, recyclerOrientation) },
            decorations = listOf(
                stickyHeaderDecoration,
            ),
        )
    }

    private fun buildGroupViewModels(
        noteEntry: Map.Entry<NotePriority, List<Note>>,
        listOrientation: LinearOrientation,
    ): Sequence<Recyclable> = when (listOrientation) {
        LinearOrientation.HORIZONTAL -> buildHorizontalGroupViewModels(noteEntry)
        LinearOrientation.VERTICAL -> buildVerticalGroupViewModels(noteEntry)
    }

    private fun buildHorizontalGroupViewModels(noteEntry: Map.Entry<NotePriority, List<Note>>): Sequence<Recyclable> =
        sequence {
            yield(
                HeaderColumnViewModel(
                    id = noteEntry.key.ordinal.toLong(),
                    text = noteEntry.key.name,
                )
            )
            noteEntry.value.forEach { note: Note ->
                yield(
                    NoteColumnViewModel(
                        id = note.id,
                        text = note.text,
                        color = note.color,
                    )
                )
            }
        }

    private fun buildVerticalGroupViewModels(noteEntry: Map.Entry<NotePriority, List<Note>>): Sequence<Recyclable> =
        sequence {
            yield(
                HeaderRowViewModel(
                    id = noteEntry.key.ordinal.toLong(),
                    text = noteEntry.key.name,
                )
            )
            noteEntry.value.forEach { note: Note ->
                yield(
                    NoteRowViewModel(
                        id = note.id,
                        text = note.text,
                        color = note.color,
                    )
                )
            }
        }

}
