package com.matbadev.dabirva.example.ui.item

import android.os.Parcelable
import com.matbadev.dabirva.example.data.Note
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemActivityArguments(
    val items: List<Note>,
) : Parcelable
