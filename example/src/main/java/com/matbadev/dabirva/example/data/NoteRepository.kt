package com.matbadev.dabirva.example.data

class NoteRepository {

    fun getNotes(): List<Note> {
        return NoteGenerator.generateNotes()
    }

}
