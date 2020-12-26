package com.matbadev.dabirva.example.data

class NoteRepository {

    private val noteIterable: Iterable<Note> = Iterable { NoteGenerator() }

    fun getNotes(): List<Note> {
        return noteIterable.take(50)
    }

    private class NoteGenerator : Iterator<Note> {

        private val priorities: Array<NotePriority> = NotePriority.values()

        private var noteId: Long = 0

        override fun hasNext(): Boolean {
            return true
        }

        override fun next(): Note {
            noteId++
            return Note(
                id = noteId,
                text = "Note #$noteId",
                priority = priorities.random(),
            )
        }

    }

}
