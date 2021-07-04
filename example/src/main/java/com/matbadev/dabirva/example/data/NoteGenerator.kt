package com.matbadev.dabirva.example.data

object NoteGenerator {

    private val MATERIAL_COLORS_100: List<Int> = listOf(
        0xffcfd8dc.toInt(),
        0xffffcdd2.toInt(),
        0xffe1bee7.toInt(),
        0xffc5cae9.toInt(),
        0xffb3e5fc.toInt(),
        0xffb2dfdb.toInt(),
        0xffdcedc8.toInt(),
        0xfffff9c4.toInt(),
        0xffffe0b2.toInt(),
        0xffd7ccc8.toInt(),
    )

    fun generateNotes(notesPerPriority: Int = MATERIAL_COLORS_100.size): List<Note> {
        return NotePriority.values().flatMapIndexed { priorityIndex: Int, priority: NotePriority ->
            Iterable {
                NoteIterator(
                    notesCount = notesPerPriority,
                    initialNoteId = (priorityIndex * notesPerPriority).toLong(),
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

        private val lastNoteId: Long = initialNoteId + notesCount - 1

        override fun hasNext(): Boolean {
            return nextNoteId <= lastNoteId
        }

        override fun next(): Note {
            val noteId: Long = nextNoteId++
            return Note(
                id = noteId,
                text = "Note $noteId",
                color = MATERIAL_COLORS_100[noteId.toInt() % MATERIAL_COLORS_100.size],
                priority = priority,
            )
        }

    }

}
