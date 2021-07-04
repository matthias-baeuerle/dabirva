package com.matbadev.dabirva.example.data

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Long,
    val text: String,
    @ColorInt val color: Int,
    val priority: NotePriority,
) : Parcelable
