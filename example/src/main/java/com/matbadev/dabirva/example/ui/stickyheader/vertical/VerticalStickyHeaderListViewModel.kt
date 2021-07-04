package com.matbadev.dabirva.example.ui.stickyheader.vertical

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel
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
) : BaseScreenViewModel<Parcelable, VerticalStickyHeaderListEvent>() {

    lateinit var items: List<ItemViewModel>

    lateinit var itemDecorations: List<RecyclerView.ItemDecoration>

    override fun initWithArguments(arguments: Parcelable?) {
        super.initWithArguments(arguments)
        items = noteRepository.getNotes()
            .groupBy { note -> note.priority }
            .flatMap { noteEntry -> buildVerticalGroupViewModels(noteEntry) }
        itemDecorations = listOf(
            VerticalStickyHeaderDecoration(
                headerPositionProvider = ItemHeaderProvider { it is HeaderRowViewModel },
            ),
        )
    }

    private fun buildVerticalGroupViewModels(noteEntry: Map.Entry<NotePriority, List<Note>>): Sequence<ItemViewModel> {
        return sequence {
            yield(HeaderRowViewModel(
                priority = noteEntry.key,
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
