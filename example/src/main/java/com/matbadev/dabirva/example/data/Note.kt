package com.matbadev.dabirva.example.data

import androidx.annotation.ColorInt

data class Note(
    val id: Long,
    val text: String,
    @ColorInt val color: Int,
    val priority: NotePriority,
)
