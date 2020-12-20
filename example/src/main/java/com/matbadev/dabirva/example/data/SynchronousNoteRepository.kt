package com.matbadev.dabirva.example.data

class SynchronousNoteRepository {

    fun getNotes(): List<Note> {
        return NoteIterable().take(50).toList()
    }

    private class NoteIterable : Iterable<Note> {
        override fun iterator(): Iterator<Note> {
            return NoteIterator()
        }
    }

    private class NoteIterator : Iterator<Note> {
        var noteId: Long = 0

        override fun hasNext(): Boolean {
            return true
        }

        override fun next(): Note {
            noteId++
            return Note(noteId, "Note #$noteId created from a synchronous repository")
        }
    }

}
