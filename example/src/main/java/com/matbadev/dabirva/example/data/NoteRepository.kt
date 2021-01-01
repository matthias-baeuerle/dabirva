package com.matbadev.dabirva.example.data

class NoteRepository {

    fun getNotes(): List<Note> {
        return generateNotes(10)
    }

    private fun generateNotes(notesPerPriority: Int): List<Note> {
        return NotePriority.values().flatMapIndexed { priorityIndex: Int, priority: NotePriority ->
            Sequence {
                NoteIterator(
                    notesCount = notesPerPriority,
                    initialNoteId = (priorityIndex * notesPerPriority) + 1L,
                    priority = priority,
                )
            }
        }
    }

    private class NoteIterator(
        notesCount: Int,
        initialNoteId: Long,
        private val priority: NotePriority,
    ) : Iterator<Note> {

        private var nextNoteId: Long = initialNoteId

        private var lastNoteId: Long = initialNoteId + notesCount - 1

        override fun hasNext(): Boolean {
            return nextNoteId <= lastNoteId
        }

        override fun next(): Note {
            val noteId: Long = nextNoteId++
            return Note(
                id = noteId,
                text = "Note #$noteId",
                priority = priority,
            )
        }

    }

}
