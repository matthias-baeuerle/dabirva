package com.matbadev.dabirva.example.ui.simple

import android.os.Parcelable
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.NoteViewModel

class SimpleListActivityViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<Parcelable, SimpleListActivityEvent>() {

    lateinit var dabirvaData: DabirvaData

    override fun initWithArguments(arguments: Parcelable?) {
        super.initWithArguments(arguments)
        dabirvaData = DabirvaData(
            items = noteRepository.getNotes().map { note: Note ->
                NoteViewModel(
                    id = note.id,
                    text = note.text,
                )
            },
        )
    }

}
