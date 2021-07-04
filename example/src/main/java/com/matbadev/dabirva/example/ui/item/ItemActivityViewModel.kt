package com.matbadev.dabirva.example.ui.item

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.util.NonNullObservableField

class ItemActivityViewModel : BaseScreenViewModel<ItemActivityArguments, ItemActivityEvent>() {

    val items = NonNullObservableField<List<ItemViewModel>>(listOf())

    override fun initWithArguments(arguments: ItemActivityArguments?) {
        super.initWithArguments(arguments)
        if (arguments == null) return
        items.value = arguments.items.map { note: Note ->
            NoteViewModel(
                id = note.id,
                text = note.text,
            )
        }
    }

}
